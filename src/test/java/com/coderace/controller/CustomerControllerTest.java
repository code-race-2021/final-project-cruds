package com.coderace.controller;

import com.coderace.model.dtos.CustomerRequestDTO;
import com.coderace.model.dtos.CustomerResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.CustomerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CustomerController.class)
@DisplayName("CustomerController test | Unit")
class CustomerControllerTest {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService service;

    @Test
    @DisplayName("create | ok")
    void createOk() throws Exception {
        // given
        final CustomerRequestDTO request = new CustomerRequestDTO();
        final CustomerResponseDTO expectedResponse = new CustomerResponseDTO();

        when(service.create(request)).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn();

        final CustomerResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);

        // then
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("create | when service throws BadRequestException | bad request")
    void createBadRequest() throws Exception {
        // given
        final CustomerRequestDTO request = new CustomerRequestDTO();

        final BadRequestException expectedException = new BadRequestException("test-message");

        when(service.create(request)).thenThrow(expectedException);

        // when
        final MvcResult result = mvc.perform(post("/customer/create")
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
        final List<CustomerResponseDTO> expectedResponse = new ArrayList<>();

        when(service.getAll()).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(get("/customer"))
                .andReturn();

        final List<CustomerResponseDTO> actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CustomerResponseDTO>>(){});

        // then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("getByDni | ok")
    void getByDniOk() throws Exception {
        // given
        final CustomerResponseDTO expectedResponse = new CustomerResponseDTO();

        when(service.getByDni(1L)).thenReturn(expectedResponse);

        // when
        final MvcResult result = mvc.perform(get("/customer/1"))
                .andReturn();

        final CustomerResponseDTO actualResponse =
                objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponseDTO.class);

        // then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("getByDni | not found")
    void getBySkuNotFound() throws Exception {
        // given
        final BadRequestException expectedException = new BadRequestException(HttpStatus.NOT_FOUND.value(), "test-message");

        when(service.getByDni(1L)).thenThrow(expectedException);

        // when
        final MvcResult result = mvc.perform(get("/customer/1"))
                .andReturn();

        // then
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals(expectedException.getMessage(), result.getResponse().getContentAsString());
    }
}