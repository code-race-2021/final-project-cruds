package com.coderace.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTypeTest {
    @Test
    @DisplayName("fromCode | ok")
    void fromCodeOk() {
        final String codeWarranty = "warranty";
        final Optional<ServiceType> fromCodeWarranty = ServiceType.fromCode(codeWarranty);

        final String codeDevolution = "devolution";
        final Optional<ServiceType> fromCodeDevolution = ServiceType.fromCode(codeDevolution);

        assertEquals(ServiceType.WARRANTY, fromCodeWarranty.get());
        assertEquals(ServiceType.DEVOLUTION, fromCodeDevolution.get());
    }

    @Test
    @DisplayName("fromCode | not found")
    void fromCodeNotFound() {
        final String code = "x";
        final Optional<ServiceType> ServiceFromCode = ServiceType.fromCode(code);

        assertEquals(Optional.empty(), ServiceFromCode);
    }

    @Test
    @DisplayName("getMultiplier")
    void getMultiplierOk() {
        assertEquals(10, ServiceType.WARRANTY.getMultiplier());
        assertEquals(20, ServiceType.DEVOLUTION.getMultiplier());
    }

    @Test
    @DisplayName("getCode")
    void getCodeOk() {
        assertEquals("warranty", ServiceType.WARRANTY.getCode());
        assertEquals("devolution", ServiceType.DEVOLUTION.getCode());
    }
}

