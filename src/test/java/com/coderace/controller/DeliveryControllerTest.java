package com.coderace.controller;

import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.DeliveryService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = DeliveryController.class)
@DisplayName("DeliveryController test | Unit")
class DeliveryControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DeliveryService service;

    @Test
    @DisplayName("create | ok")
    void createOk() throws Exception {
        // given
        final DeliveryRequestDTO request = new DeliveryRequestDTO();
        final DeliveryResponseDTO expectedResponse = new DeliveryResponseDTO();

        when(service.create(request)).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(post("/delivery/create")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        final DeliveryResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), DeliveryResponseDTO.class);

        // then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create | when service throws BadRequestException | bad request")
    void createBadRequest() throws Exception {
        // given
        final DeliveryRequestDTO request = new DeliveryRequestDTO();

        final BadRequestException expectedException = new BadRequestException("test-message");

        when(service.create(request)).thenThrow(expectedException);

        // when
        final MvcResult result = mvc.perform(post("/delivery/create")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        // then
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertEquals(expectedException.getMessage(), result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("getAll | ok")
    void getAllOk() throws Exception {
        // given
        final List<DeliveryResponseDTO> expectedResponse = new ArrayList<>();

        when(service.getAll(false)).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(get("/delivery"))
                .andReturn();

        final List<DeliveryResponseDTO> actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DeliveryResponseDTO>>(){});

        // then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }
}