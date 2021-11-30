package com.coderace.delivery;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/* Crear clase y @Entity Delivery (table = 'deliveries')
    -code: String
        No puede tener caracteres especiales sólo letras y números
        Persistir en mayúsculas

    -type: DeliveryType (enum) → REGULAR / EXPRESS
        code: String
        delay: int, REGULAR = 3, EXPRESS = 1
        cost: double, REGULAR = 5 (usd), EXPRESS = 20 (usd)
        fromCode(String): DeliveryType. → factoryMethod static

    -date: LocalDateTime // día de entrega, por defecto null

    Crear controller, service y repository asociados.

    Implementar los siguientes endpoints:
        create() → POST → Recibe un @RequestBody DeliveryRequestDTO con las properties de un service, lo crea y devuelve un ResponseEntity<> con la respuesta DeliveryResponseDTO
        getAll() → GET → Devuelve un ResponseEntity<> con un listado de DeliveryResponseDTO con todos los services guardados.

    Consideraciones:
        Todas las validaciones deben realizarse en el service
        No pasar instancias de la entidad entre capas, utilizar siempre DTOs
        Agregar un test unitario para el controller y el service                     */

@Entity
@Table(name = "deliveries")
public class Delivery {

    final String code;
    final DeliveryType type;
    final LocalDateTime date;


    public Delivery(String code, DeliveryType type) {
        this.code = code;
        this.type = type;
        this.date = null;
    }
}

