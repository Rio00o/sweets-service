package com.example.sweets;

public class SweetNotFoundException extends RuntimeException {
    public SweetNotFoundException(String message) {
        super(message);
    }
}
