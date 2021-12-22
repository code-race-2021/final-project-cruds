package com.coderace.service;

public class ValidationUtils {
    public static boolean hasSpecialCharacters(String value) {

        return !value.matches("[a-zA-Z0-9]*");
    }
}

