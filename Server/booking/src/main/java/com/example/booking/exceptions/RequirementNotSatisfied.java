package com.example.booking.exceptions;

public class RequirementNotSatisfied extends RuntimeException{
    public RequirementNotSatisfied(String message) {
        super(message);
    }
}
