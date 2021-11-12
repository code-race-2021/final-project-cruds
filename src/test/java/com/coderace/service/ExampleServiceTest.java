package com.coderace.service;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.entities.Example;
import com.coderace.model.enums.ExampleEnum;
import com.coderace.repository.ExampleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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

        final Example example = new Example(1L, 2D, "a", LocalDateTime.MIN, ExampleEnum.B);

        when(repository.save(any(Example.class))).thenReturn(example);

        final ExampleResponseDTO responseDTO = service.create(requestDTO);

        assertEquals(service.buildExampleResponseDTO(example), responseDTO);
    }

}