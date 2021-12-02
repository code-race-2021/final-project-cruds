package com.coderace.model.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeliveryRequestDTO {
    private String code;
    private String type;

    // ¿tengo que agregar date, o no? ¿dónde seteo la fecha?

    public String getCode() {
        return code;
    }

    public DeliveryRequestDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeliveryRequestDTO setType(String type) {
        this.type = type;
        return this;
    }
}
