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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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
    @DisplayName("getAll | without 'greaterThan' filter | ok")
    void getAllWithoutGreaterThanFilterOk() {
        final Example example = this.defaultExample();

        final List<Example> all = Collections.singletonList(example);

        when(repository.findAll()).thenReturn(all);

        final List<ExampleResponseDTO> result = service.getAll(null);

        assertEquals(1, result.size());
        assertEquals(service.buildExampleResponseDTO(example), result.get(0));
    }

    @Test
    @DisplayName("getAll | with 'greaterThan' filter | ok")
    void getAllWithGreaterThanFilterOk() {
        final Example exampleShouldBeSkipped = this.defaultExample();

        final Example exampleShouldBeConsidered =
                new Example(exampleShouldBeSkipped.getLongValue() + 1, 2D, "a", LocalDateTime.MIN, ExampleEnum.B);

        final List<Example> all = Arrays.asList(exampleShouldBeSkipped, exampleShouldBeConsidered);

        when(repository.findAll()).thenReturn(all);

        final List<ExampleResponseDTO> result = service.getAll(exampleShouldBeSkipped.getLongValue());

        assertEquals(1, result.size());
        assertEquals(service.buildExampleResponseDTO(exampleShouldBeConsidered), result.get(0));
    }

    @Test
    @DisplayName("getByLongValue | ok")
    void getByLongValueOk() {
        final Example example = this.defaultExample();

        when(repository.getByLongValue(1L)).thenReturn(Optional.of(example));

        final ExampleResponseDTO result = service.getByLongValue(1L);

        assertEquals(service.buildExampleResponseDTO(example), result);
    }

    @Test
    @DisplayName("getByLongValue | not found | should throw BadRequestException")
    void getByLongValueNotFound() {
        when(repository.getByLongValue(1L)).thenReturn(Optional.empty());

        final BadRequestException exception = assertThrows(BadRequestException.class, () -> service.getByLongValue(1L));

        assertAll("Expected exception",
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode()),
                () -> assertEquals(String.format("Example with long_value [%s] not found", 1L), exception.getMessage())
        );
    }

    private Example defaultExample() {
        return new Example(1L, 2D, "a", LocalDateTime.MIN, ExampleEnum.B);
    }
}