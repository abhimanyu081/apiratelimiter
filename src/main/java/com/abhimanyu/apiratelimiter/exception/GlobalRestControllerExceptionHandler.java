package com.abhimanyu.apiratelimiter.exception;

import com.abhimanyu.apiratelimiter.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalRestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiAccessCountExhausted.class)
    public ResponseEntity<ApiResponseDto<Object>> handleExceptions(ApiAccessCountExhausted exception, WebRequest webRequest) {
        ApiResponseDto<Object> apiResponseDto = new ApiResponseDto<>();
        apiResponseDto.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        apiResponseDto.setDateTime(LocalDateTime.now());
        apiResponseDto.setErrorMessage(exception.getMessage());
        ResponseEntity<ApiResponseDto<Object>> entity = new ResponseEntity<>(apiResponseDto, HttpStatus.TOO_MANY_REQUESTS);
        return entity;
    }
}