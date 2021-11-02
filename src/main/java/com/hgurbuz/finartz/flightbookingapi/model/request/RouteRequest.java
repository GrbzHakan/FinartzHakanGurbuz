package com.hgurbuz.finartz.flightbookingapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RouteRequest {
    @NotNull(message = "Arrival city field is required")
    @Size(min = 1, message = "Arrival city field is required")
    private String arrivalCity;

    @NotNull(message = "Departure city field is required")
    @Size(min = 1, message = "Departure city field is required")
    private String departureCity;
}
