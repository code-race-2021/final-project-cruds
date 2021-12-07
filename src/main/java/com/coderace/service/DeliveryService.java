package com.coderace.service;

import com.coderace.delivery.Delivery;
import com.coderace.model.enums.DeliveryType;
import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryService {
    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public DeliveryResponseDTO create(DeliveryRequestDTO requestDTO) {
        final DeliveryType type = resolveType(requestDTO.getType());
        final String code = resolveCode(requestDTO.getCode());

        final Delivery delivery = new Delivery(code, type);

        repository.save(delivery);
        return buildDeliveryResponseDTO(delivery);
    }

    public List<DeliveryResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildDeliveryResponseDTO).collect(Collectors.toList());
    }

    private String resolveCode(String code) {
        int codeLength = code.length();
        for (int i = 0; i < codeLength; i++) {
            if (!Character.isLetterOrDigit(code.charAt(i))) {
                throw new BadRequestException("Invalid code: " + code);
            }
        }
        return code.toUpperCase();
    }

    private DeliveryType resolveType(String type) {
        return DeliveryType.fromCode(type)
                .orElseThrow(() -> new BadRequestException("Invalid delivery type: " + type));
    }

    protected DeliveryResponseDTO buildDeliveryResponseDTO(Delivery delivery) {
        final DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();

        responseDTO
                .setCode(delivery.getCode())
                .setType(String.valueOf(delivery.getType()));

        return responseDTO;
    }
 }
