package com.coderace.controller;

import com.coderace.model.dtos.ExampleRequestDTO;
import com.coderace.model.dtos.ExampleResponseDTO;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    ExampleService service;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ExampleRequestDTO requestDTO) {
        try {
            final ExampleResponseDTO responseDTO = this.service.create(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam (required = false) Long greaterThan) {
        return ResponseEntity.ok().body(this.service.getAll(greaterThan));
    }

    @GetMapping("/{longValue}")
    public ResponseEntity<Object> getByLongValue(@PathVariable long longValue) {
        try {
            return ResponseEntity.ok().body(this.service.getByLongValue(longValue));
        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
