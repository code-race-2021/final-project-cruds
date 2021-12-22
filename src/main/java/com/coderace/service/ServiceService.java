package com.coderace.service;

import com.coderace.model.dtos.ServiceRequestDTO;
import com.coderace.model.dtos.ServiceResponseDTO;
import com.coderace.model.entities.Service;
import com.coderace.model.enums.ServiceType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ServiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceResponseDTO create(ServiceRequestDTO requestDTO) {
        this.validate(requestDTO);


        final ServiceType serviceType = resolveServiceType(requestDTO.getServiceType());

        final Service service = new Service(requestDTO.getSku(), serviceType, requestDTO.getDays());

        return buildServiceResponseDTO(repository.save(service));
    }

    public List<ServiceResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildServiceResponseDTO).collect(Collectors.toList());
    }

    private ServiceType resolveServiceType(String serviceTypeCode) {
        return ServiceType.fromCode(serviceTypeCode)
                .orElseThrow(() -> new BadRequestException("Invalid service type: " + serviceTypeCode));
    }

    private void validate(ServiceRequestDTO requestDTO) {
        if (ValidationUtils.hasSpecialCharacters(requestDTO.getSku())) {
            throw new BadRequestException("sku must not have special characters");
        }
        if (requestDTO.getDays() <= 0) {
            throw new BadRequestException("Days must be greater than 0");
        }
    }


    protected ServiceResponseDTO buildServiceResponseDTO(Service service) {
        final ServiceResponseDTO responseDTO = new ServiceResponseDTO();

        responseDTO
                .setServiceType(service.getServiceType().getCode())
                .setSku(service.getSku())
                .setDays(service.getDays());

        return responseDTO;
    }


}
