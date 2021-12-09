package com.coderace.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryTypeTest {

    @Test
    @DisplayName("DeliveryType | fromCode | ok")
    void deliveryTypeFromCodeOk() {
    final String code = "express";

    final Optional<DeliveryType> deliveryFromCode = DeliveryType.fromCode(code);

    assertEquals(DeliveryType.EXPRESS, deliveryFromCode.get());
    }

    @Test
    @DisplayName("DeliveryType | fromCode | not found")
    void deliveryTypeFromCodeNotFound() {
        final String code = "x";

        final Optional<DeliveryType> deliveryFromCode = DeliveryType.fromCode(code);

        assertEquals(Optional.empty(), deliveryFromCode);
    }

}