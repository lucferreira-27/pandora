package com.panadora.pandora.controller;

import com.panadora.pandora.controller.dtos.ErrorMessage;
import com.panadora.pandora.service.exceptions.BadRequestException;
import com.panadora.pandora.service.exceptions.NotFoundException;
import com.panadora.pandora.service.exceptions.RouteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
@EnableWebMvc
@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorMessage> badRequestException(NotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> routeNotFoundException(WebRequest request) {
        return   routeNotFoundException(new RouteNotFoundException(),request);
    }

    public ResponseEntity<ErrorMessage> routeNotFoundException(RouteNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=",""));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
