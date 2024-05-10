package com.example.barber.Exception;

public class PasswordConfirmationException extends RuntimeException {
    public PasswordConfirmationException(String message) {
        super(message);
    }
}