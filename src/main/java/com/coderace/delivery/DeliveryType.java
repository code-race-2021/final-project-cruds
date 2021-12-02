package com.coderace.delivery;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.stream;


public enum DeliveryType {
    REGULAR("regular", 3, 5d),
    EXPRESS("express", 1 ,20d);

    private final String code;
    private int delay;
    private double cost;


    DeliveryType(String code, Integer delay, Double cost) {
        this.code = code;
        this.delay = delay;
        this.cost = cost;
    }


    // factory method
    public static Optional<DeliveryType> fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst();
    }

    public String getCode() {
        return code;
    }

    public int getDelay() {
        return delay;
    }

    public double getCost() {
        return cost;
    }
}
