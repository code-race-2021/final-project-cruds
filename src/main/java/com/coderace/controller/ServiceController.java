package com.coderace.controller;

import com.coderace.model.dtos.ServiceRequestDTO;
import com.coderace.model.dtos.ServiceResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ServiceRequestDTO requestDTO) {
        try {
            final ServiceResponseDTO responseDTO = this.service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }


}

