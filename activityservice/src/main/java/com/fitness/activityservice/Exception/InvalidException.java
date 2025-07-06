package com.fitness.activityservice.Exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String invalidUser) {
        super(invalidUser);
    }
}
