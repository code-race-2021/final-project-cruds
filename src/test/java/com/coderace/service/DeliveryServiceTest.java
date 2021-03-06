package com.coderace.service;

import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.entities.Delivery;
import com.coderace.model.entities.Example;
import com.coderace.model.enums.DeliveryType;
import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;
import org.hibernate.collection.internal.PersistentList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("DeliveryService test | unit")
@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    DeliveryRepository repository;

    @InjectMocks
    DeliveryService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final DeliveryRequestDTO requestDTO = new DeliveryRequestDTO()
                .setCode("code1")
                .setType(DeliveryType.REGULAR.getCode());

        final Delivery delivery = this.defaultDelivery();

        when(repository.save(any(Delivery.class))).thenReturn(delivery);

        final DeliveryResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildDeliveryResponseDTO(delivery), responseDTO);
    }


    @Test
    @DisplayName("create | given invalid code value | should throw BadRequestException")
    void createInvalidCodeValueOk() {
        final DeliveryRequestDTO requestDTO = new DeliveryRequestDTO().setCode("code_1").setType("regular");

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid code: code_1", exception.getMessage())
        );
    }


    @Test
    @DisplayName("create | given invalid delivery type value | should throw BadRequestException")
    void createInvalidEnumValueOk() {
        final String invalidEnumString = "c";

        final DeliveryRequestDTO requestDTO = new DeliveryRequestDTO()
                .setCode("code1")
                .setType(invalidEnumString);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid delivery type: " + invalidEnumString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("buildDeliveryResponseDTO | ok")
    void buildDeliveryResponseDTO() {
        final String code = "code1";
        final DeliveryType type = DeliveryType.REGULAR;
        final int id = 0;

        final Delivery delivery = new Delivery(code, type);

        final DeliveryResponseDTO dto = service.buildDeliveryResponseDTO(delivery);

        assertAll("Expected dto",
                () -> assertEquals(code, dto.getCode()),
                () -> assertEquals(type.getCode(), dto.getType()),
                () -> assertEquals(id, dto.getId())
        );
    }

    @Test
    @DisplayName("getByCode | ok")
    void getByCodeOk() {
        final Delivery delivery = this.defaultDelivery();

        when(repository.getByCode("code1")).thenReturn(Optional.of(delivery));

        final DeliveryResponseDTO responseDTO = service.getByCode("code1");

        assertEquals(service.buildDeliveryResponseDTO(delivery), responseDTO);
    }

    @Test
    @DisplayName("getByCode | not found | should throw BadRequestException")
    void getByCodeNotFound() {
        when(repository.getByCode("code1")).thenReturn(Optional.empty());

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.getByCode("code1"));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode()),
                () -> assertEquals(String.format("Delivery with code [code1] not found"), exception.getMessage())
        );
    }

    @Test
    @DisplayName("getAll | all deliveries")
    void getAllDeliveries() {
        final LocalDateTime deliveryDateNotAvailable = LocalDateTime.now();

        final Delivery deliveryWithoutDate = this.defaultDelivery();
        final Delivery deliveryWithDate = this.defaultDelivery();
        deliveryWithDate.setDate(deliveryDateNotAvailable);

        final List<Delivery> all = new ArrayList<>();
        all.add(deliveryWithoutDate);
        all.add(deliveryWithDate);

        when(repository.findAll()).thenReturn(all);

        final List<DeliveryResponseDTO> result = service.getAll(false);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("getAll | deliveries not available")
    void getAllNotAvailable() {
        final LocalDateTime deliveryDateNotAvailable = LocalDateTime.now();

        final Delivery deliveryWithoutDate = this.defaultDelivery();
        final Delivery deliveryWithDate = this.defaultDelivery();
        deliveryWithDate.setDate(deliveryDateNotAvailable);

        final List<Delivery> all = new ArrayList<>();
        all.add(deliveryWithoutDate);
        all.add(deliveryWithDate);

        when(repository.findAll()).thenReturn(all);

        final List<DeliveryResponseDTO> result = service.getAll(true);

        assertEquals(1, result.size());
    }

    private Delivery defaultDelivery() {
        return new Delivery("code1", DeliveryType.REGULAR);
    }
}