package com.hgurbuz.finartz.flightbookingapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class FlightRequest {

    @NotNull(message = "Company id  is required")
    private Long companyId;
    @NotNull(message = "Route id  is required")
    private Long routeId;
    @NotNull(message = "Max capacity is required")
    private int maxCapacity;
    @NotNull(message = "Price is required")
    private float price;
    @NotNull(message = "Departure Time is required")
    private Date departureTime;
    @NotNull(message = "Arrival Time is required")
    private Date arrivalTime;
}
