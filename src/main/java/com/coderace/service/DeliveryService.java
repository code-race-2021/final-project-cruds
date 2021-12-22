package com.coderace.service;

import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.entities.Delivery;
import com.coderace.model.enums.DeliveryType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) { this.repository = repository;
    }

    public DeliveryResponseDTO create(DeliveryRequestDTO requestDTO) {
        final DeliveryType type = resolveType(requestDTO.getType());
        final String code = resolveCode(requestDTO.getCode());

        final Delivery deliveryBeforePersistence = new Delivery(code, type);

        final Delivery deliveryAfterPersistence = repository.save(deliveryBeforePersistence);

        return buildDeliveryResponseDTO(deliveryAfterPersistence);
    }

    public List<DeliveryResponseDTO> getAll(boolean available) {
            return this.repository.findAll().stream()
                    .filter(delivery -> this.isAvailable(delivery.getDate(), available))
                    .map(this::buildDeliveryResponseDTO)
                    .collect(Collectors.toList());
    }

    private boolean isAvailable(LocalDateTime date, boolean available) {
            return !available || date == null;
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

    public List<DeliveryResponseDTO> getAll() {
        return this.repository.findAll().stream().map(this::buildDeliveryResponseDTO).collect(Collectors.toList());
    }

    private DeliveryType resolveType(String type) {
        return DeliveryType.fromCode(type)
                .orElseThrow(() -> new BadRequestException("Invalid delivery type: " + type));
    }

    protected DeliveryResponseDTO buildDeliveryResponseDTO(Delivery delivery) {
        final DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();

        responseDTO
                .setCode(delivery.getCode())
                .setType(delivery.getType().getCode())
                .setId(delivery.getId());

        return responseDTO;
    }

    public DeliveryResponseDTO getByCode(String code) {
        return this.repository.getByCode(code)
                .map(this::buildDeliveryResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Delivery with code [%s] not found", code)));
    }
 }
