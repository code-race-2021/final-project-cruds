package com.coderace.model.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DeliveryResponseDTO {
    private int id;
    private String code;
    private String type;

    public DeliveryResponseDTO setId(int id) {
        this.id = id;
        return this;
    }

    public int getId() { return id; }

    public DeliveryResponseDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCode() { return code; }
    
    public DeliveryResponseDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

}
