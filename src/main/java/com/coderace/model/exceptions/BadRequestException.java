package com.coderace.model.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    final int statusCode;

    public BadRequestException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public BadRequestException(String message) {
        this(HttpStatus.BAD_REQUEST.value(), message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
