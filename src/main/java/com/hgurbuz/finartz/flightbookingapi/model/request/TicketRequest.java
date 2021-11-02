package com.hgurbuz.finartz.flightbookingapi.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TicketRequest {
    @NotNull(message = "Credit card field is required")
    @Size(min = 16,message = "Credit card  field is required")
    private String creditCard;
    @NotNull(message = "Number of seats field is required")
    private int numOfSeats;
    @NotNull(message = "Passenger name field is required")
    @Size(min = 1,message = "Passenger name  field is required")
    private String passengerName;

    @NotNull(message = "Passenger surname field is required")
    @Size(min = 1,message = "Passenger surname  field is required")
    private String passengerSurname;

    @NotNull(message = "Flight id field is required")
    private Long flightId;
}
