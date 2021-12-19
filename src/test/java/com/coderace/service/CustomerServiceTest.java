package com.coderace.service;

import com.coderace.model.dtos.CustomerRequestDTO;
import com.coderace.model.dtos.CustomerResponseDTO;
import com.coderace.model.entities.Customer;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("CustomerService test | unit")
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    
    private static final String VALID_EMAIL = "test-email@email.com";

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        requestDTO.setName("name");
        requestDTO.setDni(1L);
        requestDTO.setEmail(VALID_EMAIL);

        final Customer customer = this.defaultCustomer();

        when(repository.save(any(Customer.class))).thenReturn(customer);

        final CustomerResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildCustomerResponseDTO(customer), responseDTO);
    }

    @Test
    @DisplayName("create | given negative dni | should throw BadRequestException")
    void createWithNegativeDni() {
        final CustomerRequestDTO requestDTO = new CustomerRequestDTO();

        requestDTO.setDni(-1L);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Dni must be positive", exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid email | should throw BadRequestException")
    void createInvalidEmail() {
        final String invalidEmail = VALID_EMAIL.replaceAll("@", "");
        
        final CustomerRequestDTO requestDTO = new CustomerRequestDTO();

        requestDTO.setEmail(invalidEmail);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Email is invalid", exception.getMessage())
        );
    }
    

    @Test
    @DisplayName("buildCustomerResponseDTO | ok")
    void buildCustomerResponseDTO() {
        final String name = "name";
        final long dni = 1L;
        final String email = VALID_EMAIL;

        final Customer customer = new Customer(name, dni, email);

        final CustomerResponseDTO dto = service.buildCustomerResponseDTO(customer);

        assertAll("Expected dto",
                () -> assertEquals(name, dto.getName()),
                () -> assertEquals(dni, dto.getDni()),
                () -> assertEquals(dni, dto.getDni())
        );
    }

    @Test
    @DisplayName("getAll | ok")
    void getAllOk() {
        final Customer customer = this.defaultCustomer();

        final List<Customer> all = Collections.singletonList(customer);

        when(repository.findAll()).thenReturn(all);

        final List<CustomerResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(service.buildCustomerResponseDTO(customer), result.get(0));
    }

    @Test
    @DisplayName("getByDni | ok")
    void getByDniOk() {
        final Customer customer = this.defaultCustomer();

        final long dni = customer.getDni();

        when(repository.getByDni(dni)).thenReturn(Optional.of(customer));

        final CustomerResponseDTO result = service.getByDni(dni);

        assertEquals(service.buildCustomerResponseDTO(customer), result);
    }

    @Test
    @DisplayName("getByDni | not found | should throw BadRequestException")
    void getByDniNotFound() {
        final long dni = 1L;

        when(repository.getByDni(dni)).thenReturn(Optional.empty());

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.getByDni(dni));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode()),
                () -> assertEquals(String.format("Customer with dni [%s] not found", dni), exception.getMessage())
        );
    }

    private Customer defaultCustomer() {
        return new Customer("name", 1L, VALID_EMAIL);
    }
}