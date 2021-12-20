package com.coderace.model.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ServiceResponseDTO {
    private String sku;
    private String serviceType;
    private Long days;

    public Long getDays() {
        return days;
    }

    public ServiceResponseDTO setDays(Long days) {
        this.days = days;
        return this;
    }


    public String getSku() {
        return sku;
    }

    public ServiceResponseDTO setSku(String sku) {
        this.sku = sku;
        return this;
    }


    public String getServiceType() {
        return serviceType;
    }

    public ServiceResponseDTO setServiceType(String serviceType) {
        this.serviceType = serviceType;
        return this;
    }
}
