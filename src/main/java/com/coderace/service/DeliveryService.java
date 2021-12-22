package com.coderace.service;

import com.coderace.model.dtos.ServiceRequestDTO;
import com.coderace.model.entities.Delivery;
import com.coderace.model.enums.DeliveryType;
import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.enums.ServiceType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public DeliveryResponseDTO create(DeliveryRequestDTO requestDTO) {
        this.validate(requestDTO);


        final DeliveryType deliveryType = resolveType(requestDTO.getType());

        final Delivery delivery = new Delivery(requestDTO.getCode(), deliveryType);

        return buildDeliveryResponseDTO(repository.save(delivery));


    }


    public List<DeliveryResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildDeliveryResponseDTO).collect(Collectors.toList());
    }


        private void validate(DeliveryRequestDTO requestDTO) {
            if (ValidationUtils.hasSpecialCharacters(requestDTO.getCode())) {
                throw new BadRequestException("Invalid code");
            }
        }



    private DeliveryType resolveType(String type) {
        return DeliveryType.fromCode(type)
                .orElseThrow(() -> new BadRequestException("Invalid delivery type: " + type));
    }

    protected DeliveryResponseDTO buildDeliveryResponseDTO(Delivery delivery) {
        final DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();

        responseDTO
                .setCode(delivery.getCode())
                .setType(delivery.getType().getCode());

        return responseDTO;
    }
 }
