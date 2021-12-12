package com.coderace.controller;

import com.coderace.model.dtos.ProductRequestDTO;
import com.coderace.model.dtos.ProductResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ProductRequestDTO requestDTO) {
        try {
            final ProductResponseDTO responseDTO = this.service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok().body(this.service.getAll());
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Object> getBySku(@PathVariable String sku) {
        try {
            return ResponseEntity.ok().body(this.service.getBySku(sku));
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
