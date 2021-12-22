package com.coderace.service;

import com.coderace.model.dtos.CustomerRequestDTO;
import com.coderace.model.dtos.CustomerResponseDTO;
import com.coderace.model.entities.Customer;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponseDTO create(CustomerRequestDTO requestDTO) {
        this.validate(requestDTO);

        try {
            return this.buildCustomerResponseDTO(repository.save(new Customer(requestDTO.getName(), requestDTO.getDni(), requestDTO.getEmail())));
        } catch (Exception e) {
            return null;
        }
    }

    public List<CustomerResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildCustomerResponseDTO).collect(Collectors.toList());
    }

    private void validate(CustomerRequestDTO requestDTO) {
        final String errorMessage;

        if (this.isEmailInvalid(requestDTO.getEmail())) {
            errorMessage = "Email is invalid";
        } else if (this.isDniInvalid(requestDTO.getDni())) {
            errorMessage = "Dni is invalid";
        } else {
            errorMessage = null;
        }

        if (errorMessage != null) {
            throw new BadRequestException(errorMessage);
        }
    }

    private boolean isDniInvalid(Long dni) {
        return dni == null || dni <= 0 || String.valueOf(dni).length() != 8;
    }

    // a valid emails contains "@" followed by "."
    private boolean isEmailInvalid(String email) {
        if (email == null) {
            return true;
        }

        boolean invalid = false;

        final String at = "@";
        final String dot = ".";

        List<String> chars = Arrays.asList(email.split(""));

        // first check that both symbols are present
        if (!chars.containsAll(Arrays.asList(at, dot))) {
            invalid = true;
        } else { // then check that the dot is after the at
            final int atPosition = chars.indexOf(at);
            final int lastDotPosition = chars.lastIndexOf(dot);

            if (lastDotPosition < atPosition) {
                invalid = true;
            }
        }

        return invalid;
    }

    protected CustomerResponseDTO buildCustomerResponseDTO(Customer customer) {
        final CustomerResponseDTO responseDTO = new CustomerResponseDTO();

        responseDTO.setName(customer.getName());
        responseDTO.setDni(customer.getDni());
        responseDTO.setEmail(customer.getEmail());

        return responseDTO;
    }

    public CustomerResponseDTO getByDni(long dni) {
        return this.repository.getByDni(dni)
                .map(this::buildCustomerResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Customer with dni [%s] not found", dni)));
    }
}
