package com.coderace.model.entities;

import org.junit.jupiter.api.Test;

class CustomerTest {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testInitialization() {
        final Customer customer = new Customer("name", 1L, "email");
        final Customer customer2 = new Customer();


        customer.getId();
        customer.getName();
        customer.getDni();
        customer.getEmail();
    }
}