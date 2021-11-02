package com.hgurbuz.finartz.flightbookingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        return exceptionResponseCreator(HttpStatus.NOT_FOUND, errorMessages);
    }
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleAirportDoesntExistException(AirportDoesntExistException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        return exceptionResponseCreator(HttpStatus.BAD_REQUEST, errorMessages);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleException(Exception e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        return exceptionResponseCreator(HttpStatus.NOT_FOUND, errorMessages);
    }

    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleException(SeatCapacityExceedException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        return exceptionResponseCreator(HttpStatus.BAD_REQUEST, errorMessages);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(e.getMessage());

        return exceptionResponseCreator(HttpStatus.BAD_REQUEST, errorMessages);
    }

    private final ResponseEntity<ExceptionResponse> exceptionResponseCreator(HttpStatus status, List<String> errorMessages) {
        ExceptionResponse response = new ExceptionResponse(status.value(),
                errorMessages,
                System.currentTimeMillis());
        return new ResponseEntity<>(response, status);
    }
}
