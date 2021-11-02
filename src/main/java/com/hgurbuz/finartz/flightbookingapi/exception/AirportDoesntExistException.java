package com.hgurbuz.finartz.flightbookingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AirportDoesntExistException extends RuntimeException{
    public AirportDoesntExistException(String message) {
        super(message);
    }
}
