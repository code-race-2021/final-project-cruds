package com.coderace.model.entities;

import com.coderace.model.enums.DeliveryType;
import org.junit.jupiter.api.Test;

class DeliveryTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testInitialization() {
        final Delivery delivery = new Delivery("code", DeliveryType.EXPRESS);
        final Delivery delivery2 = new Delivery();


        delivery.getId();
        delivery.getCode();
        delivery.getDate();
        delivery.getType();
    }

}