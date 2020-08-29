package com.abhimanyu.apiratelimiter.exception;

public class ApiAccessCountExhausted extends Exception{

    public ApiAccessCountExhausted(String message) {
        super(message);
    }

    public ApiAccessCountExhausted(String message, Throwable cause) {
        super(message, cause);
    }
}
