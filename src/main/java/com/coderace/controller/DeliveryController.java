package com.coderace.controller;

import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;

import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    DeliveryService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody DeliveryRequestDTO requestDTO) {
        try {
            final DeliveryResponseDTO responseDTO = this.service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> getByCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok().body(this.service.getByCode(code));
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }
}
