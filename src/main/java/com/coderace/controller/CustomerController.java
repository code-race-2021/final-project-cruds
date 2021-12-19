package com.coderace.controller;

import com.coderace.model.dtos.CustomerRequestDTO;
import com.coderace.model.dtos.CustomerResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody CustomerRequestDTO requestDTO) {
        try {
            final CustomerResponseDTO responseDTO = this.service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Object> getByDni(@PathVariable Long dni) {
        try {
            return ResponseEntity.ok().body(this.service.getByDni(dni));
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    private CustomerService getService() {
        return this.service;
    }
}
