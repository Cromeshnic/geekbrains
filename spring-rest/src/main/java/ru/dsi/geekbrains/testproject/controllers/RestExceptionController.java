package ru.dsi.geekbrains.testproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.dsi.geekbrains.testproject.common.ErrorDto;
import ru.dsi.geekbrains.testproject.exceptions.RestResourceException;

@ControllerAdvice
public class RestExceptionController {
    @ExceptionHandler
    public ResponseEntity<?> exceptionInterceptor(RestResourceException exc) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
