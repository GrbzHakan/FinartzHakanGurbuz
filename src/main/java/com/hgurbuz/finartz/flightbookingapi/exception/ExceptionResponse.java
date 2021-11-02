package com.hgurbuz.finartz.flightbookingapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private int status;
    private List<String> errorMessages;
    private long timestamp;
}
