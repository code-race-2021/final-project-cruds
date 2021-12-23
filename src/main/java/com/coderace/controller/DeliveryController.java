package com.coderace.controller;

import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;

import com.coderace.model.enums.DeliveryType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.DeliveryService;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @GetMapping("/calculate/{code}")
    public ResponseEntity<Object> calculatePriceAndDateOfDelivery(@PathVariable String code,
                                                                      @RequestParam (value = "price", required = false) double price) {
        try {
            final DeliveryResponseDTO delivery = this.service.getByCode(code);
            final JSONObject jsonResponse = new JSONObject();

            if (delivery.getType().equals("regular")) {
                final double finalCostRegular = price * (DeliveryType.REGULAR.getCost() / 100);
                final LocalDateTime finalArrivalRegular = LocalDateTime.now().plusDays(DeliveryType.REGULAR.getDelay());

                jsonResponse.put("cost", finalCostRegular);
                jsonResponse.put("arrival", String.valueOf(finalArrivalRegular));

            } else if (delivery.getType().equals("express")) {
                final double finalCostExpress = price * (DeliveryType.EXPRESS.getCost() / 100);
                final LocalDateTime finalArrivalExpress = LocalDateTime.now().plusDays(DeliveryType.EXPRESS.getDelay());

                jsonResponse.put("cost", finalCostExpress);
                jsonResponse.put("arrival", String.valueOf(finalArrivalExpress));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);

        } catch (BadRequestException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam (required = false, defaultValue = "false") boolean available) {
        return ResponseEntity.ok().body(this.service.getAll(available));
    }
}

