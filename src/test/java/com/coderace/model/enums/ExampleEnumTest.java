package com.coderace.model.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExampleEnumTest {
    @Test
    @DisplayName("fromCode | ok")
    void fromCodeOk() {
        final String codeA = "a";
        final Optional<ExampleEnum> fromCodeA = ExampleEnum.fromCode(codeA);

        final String codeB = "b";
        final Optional<ExampleEnum> fromCodeB = ExampleEnum.fromCode(codeB);

        assertEquals(ExampleEnum.A, fromCodeA.get());
        assertEquals(ExampleEnum.B, fromCodeB.get());
    }

    @Test
    @DisplayName("fromCode | not found")
    void fromCodeNotFound() {
        final String code = "x";
        final Optional<ExampleEnum> fromCode = ExampleEnum.fromCode(code);

        assertEquals(Optional.empty(), fromCode);
    }

    @Test
    @DisplayName("getCode")
    void getCodeOk() {
        assertEquals("a", ExampleEnum.A.getCode());
        assertEquals("b", ExampleEnum.B.getCode());
    }
}