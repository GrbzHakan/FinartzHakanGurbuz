package com.hgurbuz.finartz.flightbookingapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AirportRequest {

    @NotNull(message = "Name field is required")
    @Size(min = 1, message = "Name field is required")
    private String name;

    @NotNull(message = "City field is required")
    @Size(min = 1, message = "City field is required")
    private String city;
}
