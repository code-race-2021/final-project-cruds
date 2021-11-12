package com.coderace.model.dtos;

public class ExampleResponseDTO {
    private Long longValue;
    private Double doubleValue;
    private String stringValue;
    private String dateValue;
    private String enumValue;

    public Long getLongValue() {
        return longValue;
    }

    public ExampleResponseDTO setLongValue(Long longValue) {
        this.longValue = longValue;
        return this;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public ExampleResponseDTO setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public String getStringValue() {
        return stringValue;
    }

    public ExampleResponseDTO setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public String getDateValue() {
        return dateValue;
    }

    public ExampleResponseDTO setDateValue(String dateValue) {
        this.dateValue = dateValue;
        return this;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public ExampleResponseDTO setEnumValue(String enumValue) {
        this.enumValue = enumValue;
        return this;
    }
}
