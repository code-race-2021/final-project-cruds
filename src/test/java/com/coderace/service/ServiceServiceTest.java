package com.coderace.service;

import com.coderace.model.dtos.ServiceRequestDTO;
import com.coderace.model.dtos.ServiceResponseDTO;
import com.coderace.model.entities.Service;
import com.coderace.model.enums.ServiceType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ServiceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DisplayName("ExampleService test | unit")
@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    ServiceRepository repository;

    @InjectMocks
    ServiceService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final ServiceRequestDTO requestDTO = new ServiceRequestDTO()
                .setSku("jose")
                .setDays(10L)
                .setServiceType(ServiceType.WARRANTY.getCode());


        final Service defaultService = this.defaultService();

        when(repository.save(any(Service.class))).thenReturn(defaultService);

        final ServiceResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildServiceResponseDTO(defaultService), responseDTO);
    }

    @Test
    @DisplayName("create | given invalid days value | should throw BadRequestException")
    void createInvalidDaysValueOk() {
        final ServiceRequestDTO requestDTO = new ServiceRequestDTO().setDays(-5L).setSku("javier");

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Days must be greater than 0", exception.getMessage())
        );
    }


    @Test
    @DisplayName("create | given invalid serviceType value | should throw BadRequestException")
    void createInvalidServiceTypeValueOk() {
        final String invalidEnumString = "c";

        final ServiceRequestDTO requestDTO = new ServiceRequestDTO()
                .setSku("eduardo")
                .setDays(10L)
                .setServiceType(invalidEnumString);


        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid service type: " + invalidEnumString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid sku value | should throw BadRequestException")
    void createInvalidSkuValueOk() {
        final String invalidSkuString = "?=¿";

        final ServiceRequestDTO requestDTO = new ServiceRequestDTO()
                .setSku(invalidSkuString)
                .setDays(10L)
                .setServiceType(ServiceType.WARRANTY.getCode());


        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("sku must not have special characters", exception.getMessage())
        );
    }


    @Test
    @DisplayName("buildServiceResponseDTO | ok")
    void buildServiceResponseDTO() {
        final long days = 1L;
        final String sku = "josefa";
        final ServiceType serviceType = ServiceType.WARRANTY;

        final Service exampleService = new Service(sku, serviceType, days);

        final ServiceResponseDTO dto = service.buildServiceResponseDTO(exampleService);

        assertAll("Expected dto",
                () -> assertEquals(days, dto.getDays()),
                () -> assertEquals(sku, dto.getSku()),
                () -> assertEquals(serviceType.getCode(), dto.getServiceType())
        );
    }


    @Test
    @DisplayName("getAll | ok")
    void getAllOk() {
        final Service exampleService = this.defaultService();

        final List<Service> all = Collections.singletonList(exampleService);

        when(repository.findAll()).thenReturn(all);


        final List<ServiceResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(service.buildServiceResponseDTO(exampleService), result.get(0));
    }


    private Service defaultService() {
        return new Service("jose", ServiceType.WARRANTY, 10L);
    }
}