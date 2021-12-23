package com.coderace.controller;

import com.coderace.model.dtos.DeliveryRequestDTO;
import com.coderace.model.dtos.DeliveryResponseDTO;

import com.coderace.model.enums.DeliveryType;
import com.coderace.model.exceptions.BadRequestException;
import com.coderace.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    // El cliente necesita obtener el presupuesto de un delivery para un valor dado en pesos
    // y además necesita saber cuándo se realizaría la entrega si se solicita en ese momento.

    // A partir de un delivery determinado (path variable sku),
    // y enviando como queryParam un precio, devuelva un json con el costo de esa entrega y la fecha en que llegaría.

    // Para esto se deberán usar los datos cost (multiplicador porcentual) y delay de DeliveryType.

    // Ej.:
    // Si el delivery es REGULAR y lo solicito el 2021-01-01 (primero de enero) para un producto que sale 200$, la respuesta debería ser así:

    // GET → /delivery/calculate/{sku}?price=200

    /*
    {
        "cost": 10, // el 5% del precio del producto
            "arrival": "2021-01-04T00.00.000" // le sumo 3 días a LocalDateTime.now()
    }
     */

    /*
    @GetMapping("/calculate/{sku}?price")
    public ResponseEntity<Object> getBySku(@PathVariable String sku, @RequestParam double price) {
        final DeliveryResponseDTO delivery = this.service.getByCode(sku);

        if ( delivery.getType().equals("regular") ) {
            final double finalCost = DeliveryType.REGULAR.getCost();
            final LocalDateTime finalArrival = LocalDateTime.now().plusDays(DeliveryType.REGULAR.getDelay());

            return null;

        } else if ( delivery.getType().equals("express") ) {
            final double finalCost = DeliveryType.EXPRESS.getCost();
            final LocalDateTime finalArrival = LocalDateTime.now().plusDays(DeliveryType.EXPRESS.getDelay());

            return null;

        }
    }

     */

    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam (required = false, defaultValue = "false") boolean available) {
        return ResponseEntity.ok().body(this.service.getAll(available));
    }
}

