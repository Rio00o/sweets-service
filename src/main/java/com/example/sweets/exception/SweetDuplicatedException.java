package com.example.sweets.exception;

public class SweetDuplicatedException extends RuntimeException {
    public SweetDuplicatedException(String message) {
        super(message);
    }
}
