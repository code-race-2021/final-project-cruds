package com.coderace.service;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.entities.Example;
import com.coderace.model.enums.ExampleEnum;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.repository.ExampleRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ExampleService test | unit")
@ExtendWith(MockitoExtension.class)
class ExampleServiceTest {

    @Mock
    ExampleRepository repository;

    @InjectMocks
    ExampleService service;

    @Test
    @DisplayName("create | ok")
    void createOk() {
        final ExampleRequestDTO requestDTO = new ExampleRequestDTO()
                .setLongValue(5L)
                .setDateValue(LocalDateTime.MIN.toString())
                .setEnumValue("b");

        final Example example = this.defaultExample();

        when(repository.save(any(Example.class))).thenReturn(example);

        final ExampleResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildExampleResponseDTO(example), responseDTO);
    }

    @Test
    @DisplayName("create | given invalid long value | should throw BadRequestException")
    void createInvalidLongValueOk() {
        final ExampleRequestDTO requestDTO = new ExampleRequestDTO().setLongValue(-5L);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("longValue must be greater than 0", exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid date value | should throw BadRequestException")
    void createInvalidDateValueOk() {
        final String invalidDateString = "not-a-date";

        final ExampleRequestDTO requestDTO = new ExampleRequestDTO()
                .setLongValue(1L)
                .setDateValue(invalidDateString);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid dateValue: " + invalidDateString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("create | given invalid enum value | should throw BadRequestException")
    void createInvalidEnumValueOk() {
        final String invalidEnumString = "c";

        final ExampleRequestDTO requestDTO = new ExampleRequestDTO()
                .setLongValue(5L)
                .setDateValue(LocalDateTime.MAX.toString())
                .setEnumValue(invalidEnumString);

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.create(requestDTO));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatusCode()),
                () -> assertEquals("Invalid enumValue: " + invalidEnumString, exception.getMessage())
        );
    }

    @Test
    @DisplayName("buildExampleResponseDTO | ok")
    void buildExampleResponseDTO() {
        final long longValue = 1L;
        final double doubleValue = 2D;
        final String stringValue = "a";
        final LocalDateTime dateValue = LocalDateTime.MIN;
        final ExampleEnum enumValue = ExampleEnum.B;

        final Example example = new Example(longValue, doubleValue, stringValue, dateValue, enumValue);

        final ExampleResponseDTO dto = service.buildExampleResponseDTO(example);

        assertAll("Expected dto",
                () -> assertEquals(longValue, dto.getLongValue()),
                () -> assertEquals(doubleValue, dto.getDoubleValue()),
                () -> assertEquals(stringValue, dto.getStringValue()),
                () -> assertEquals(dateValue.toString(), dto.getDateValue()),
                () -> assertEquals(enumValue.getCode(), dto.getEnumValue())
        );
    }

    @Test
    @DisplayName("getAll | ok")
    void getAllOk() {
        final Example example = this.defaultExample();

        final List<Example> all = Collections.singletonList(example);

        when(repository.findAll()).thenReturn(all);

        final List<ExampleResponseDTO> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(service.buildExampleResponseDTO(example), result.get(0));
    }

    private Example defaultExample() {
        return new Example(1L, 2D, "a", LocalDateTime.MIN, ExampleEnum.B);
    }
}