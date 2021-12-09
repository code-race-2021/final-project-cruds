package com.coderace.service;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.coderace.delivery.Delivery;
import com.coderace.model.enums.DeliveryType;
import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.DeliveryRepository;
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

        final Delivery delivery = new Delivery(code, type);

        final DeliveryResponseDTO dto = service.buildDeliveryResponseDTO(delivery);

        assertAll("Expected dto",
                () -> assertEquals(code, dto.getCode()),
                () -> assertEquals(type.toString(), dto.getType())   // acá tuve que pasar el enum a String para poder pasar el test, ¿es correcto?
                );
    }

    @Test
    @DisplayName("getAll | ok")
    void getAllOk() {
        final Delivery delivery = this.defaultDelivery();

        final List<Delivery> all = Collections.singletonList(delivery);

        when(repository.findAll()).thenReturn(all);

        final List<DeliveryResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(service.buildDeliveryResponseDTO(delivery), result.get(0));
    }


    private Delivery defaultDelivery() {
        return new Delivery("code1", DeliveryType.REGULAR);
    }
}