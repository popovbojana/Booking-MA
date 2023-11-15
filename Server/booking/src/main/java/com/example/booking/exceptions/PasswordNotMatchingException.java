package com.example.booking.exceptions;

public class PasswordNotMatchingException extends RuntimeException{
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
