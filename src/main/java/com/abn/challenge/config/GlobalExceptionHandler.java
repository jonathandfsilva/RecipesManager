package com.abn.challenge.config;

import org.springdoc.api.ErrorMessage;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage illegalArgumentException(IllegalArgumentException ex) {
        return new ErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorMessage(String.format("No recipes were found with the given parameters. %s", ex.getMessage()));
    }

}