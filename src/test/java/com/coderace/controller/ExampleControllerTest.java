package com.coderace.controller;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.ExampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
@DisplayName("ExampleController test | Unit")
class ExampleControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ExampleService service;
    
    @Test
    @DisplayName("create | ok")
    void createOk() throws Exception {
        // given
        final ExampleRequestDTO request = new ExampleRequestDTO();
        final ExampleResponseDTO expectedResponse = new ExampleResponseDTO();

        when(service.create(request)).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(post("/example/create")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        final ExampleResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), ExampleResponseDTO.class);

        // then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create | when service throws BadRequestException | bad request")
    void createBadRequest() throws Exception {
        // given
        final ExampleRequestDTO request = new ExampleRequestDTO();

        final BadRequestException expectedException = new BadRequestException("test-message");

        when(service.create(request)).thenThrow(expectedException);

        // when
        final MvcResult result = mvc.perform(post("/example/create")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals(expectedException.getMessage(), result.getResponse().getContentAsString());
    }
}