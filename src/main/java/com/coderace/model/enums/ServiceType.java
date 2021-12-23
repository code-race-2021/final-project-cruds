package com.coderace.model.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceType {
    WARRANTY("warranty", 10),
    DEVOLUTION("devolution", 20);

    private final String code;

    private final double multiplier;

    ServiceType(String code, double multiplier) {
        this.code = code;
        this.multiplier = multiplier;

    }

    public static Optional<ServiceType> fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst();
    }

    public String getCode() {
        return code;
    }

    public double getMultiplier() {
        return multiplier;
    }
}


