package com.coderace.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTypeTest {

    @Test
    @DisplayName("fromCode | ok")
    void fromCodeOk() {
        final String codeExpress = "express";
        final Optional<DeliveryType> deliveryFromCodeExpress = DeliveryType.fromCode(codeExpress);

        final String codeRegular = "regular";
        final Optional<DeliveryType> deliveryFromCodeRegular = DeliveryType.fromCode(codeRegular);

        assertEquals(DeliveryType.EXPRESS, deliveryFromCodeExpress.get());
        assertEquals(DeliveryType.REGULAR, deliveryFromCodeRegular.get());
    }

    @Test
    @DisplayName("fromCode | not found")
    void fromCodeNotFound() {
        final String code = "x";
        final Optional<DeliveryType> deliveryFromCode = DeliveryType.fromCode(code);

        assertEquals(Optional.empty(), deliveryFromCode);
    }

    @Test
    @DisplayName("getDelay | ok")
    void getDelayOk() {
        assertEquals(3, DeliveryType.REGULAR.getDelay());
        assertEquals(1, DeliveryType.EXPRESS.getDelay());
    }

    @Test
    @DisplayName("getCost | ok")
    void getCostOk() {
        assertEquals(5, DeliveryType.REGULAR.getCost());
        assertEquals(20, DeliveryType.EXPRESS.getCost());
    }

    @Test
    @DisplayName("getCode")
    void getCodeOk() {
        assertEquals("regular", DeliveryType.REGULAR.getCode());
        assertEquals("express", DeliveryType.EXPRESS.getCode());
    }
}