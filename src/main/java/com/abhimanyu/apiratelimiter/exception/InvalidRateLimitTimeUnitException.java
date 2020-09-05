package com.abhimanyu.apiratelimiter.exception;

public class InvalidRateLimitTimeUnitException extends  RuntimeException{
    public InvalidRateLimitTimeUnitException(String message) {
        super(message);
    }

    public InvalidRateLimitTimeUnitException(String message, Throwable cause) {
        super(message, cause);
    }
}
