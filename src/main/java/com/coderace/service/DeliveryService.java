package com.coderace.service;

import com.coderace.delivery.Delivery;
import com.coderace.delivery.DeliveryType;
import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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


    // validar si code tiene sólo letras y números. ¿Está bien?
    private String resolveCode(String code) {
        try {
            int codeLength = code.length();
            for (int i = 0; i < codeLength; i++) {
                if ((Character.isLetterOrDigit(code.charAt(i)) == false)) {
                    throw new BadRequestException("Invalid code " + code);
                }
            }
            return code.toUpperCase();      // sí es valido, devuelvo en mayúsculas
        } catch (Exception e) {
            throw new BadRequestException("Invalid code: " + code);
        }
    }

    private DeliveryType resolveType(String type) {
        return DeliveryType.fromCode(type)
                .orElseThrow(() -> new BadRequestException("Invalid enum: " + type));
    }

    protected DeliveryResponseDTO buildDeliveryResponseDTO(Delivery delivery) {
        final DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();

        responseDTO
                .setCode(delivery.getCode())
                .setDate(delivery.getDate().toString())
                .setType(String.valueOf(delivery.getType()));

        return responseDTO;
    }

    /*
    // tengo dudas sobre si esto va o no.

    public ExampleResponseDTO getByLongValue(long longValue) {
        return this.repository.getByLongValue(longValue)
                .map(this::buildExampleResponseDTO)
                .orElseThrow(() -> new BadRequestException(HttpStatus.NOT_FOUND.value(),
                        String.format("Example with long_value [%s] not found", longValue)));
    }
     */
 }
