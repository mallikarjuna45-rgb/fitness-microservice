package com.fitness.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExists.class)
    ResponseEntity<HashMap<String,String>> handleException(Exception e){
        HashMap<String,String> error = new HashMap<>();
        error.put("message",e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<HashMap<String,String>> handleExceptiona(MethodArgumentNotValidException e){
        HashMap<String,String> error = new HashMap<>();
        error.put("message",e.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
