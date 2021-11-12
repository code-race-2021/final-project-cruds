package com.coderace.model.dtos;

public class ExampleRequestDTO {
    private Long longValue;
    private Double doubleValue;
    private String stringValue;
    private String dateValue;
    private String enumValue;

    public Long getLongValue() {
        return longValue;
    }

    public ExampleRequestDTO setLongValue(Long longValue) {
        this.longValue = longValue;
        return this;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public ExampleRequestDTO setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
        return this;
    }

    public String getStringValue() {
        return stringValue;
    }

    public ExampleRequestDTO setStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public String getDateValue() {
        return dateValue;
    }

    public ExampleRequestDTO setDateValue(String dateValue) {
        this.dateValue = dateValue;
        return this;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public ExampleRequestDTO setEnumValue(String enumValue) {
        this.enumValue = enumValue;
        return this;
    }
}
