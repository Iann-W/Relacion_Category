package com.example.demointelig.exeption;

public class StockConflictException extends RuntimeException {
    public StockConflictException(String message) {
        super(message);
    }
}