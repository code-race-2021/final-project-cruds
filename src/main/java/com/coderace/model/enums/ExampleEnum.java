package com.coderace.model.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ExampleEnum {
    A("a"),
    B("b");

    private final String code;

    ExampleEnum(String code) {
        this.code = code;
    }

    public static Optional<ExampleEnum> fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst();
    }

    public String getCode() {
        return code;
    }
}
