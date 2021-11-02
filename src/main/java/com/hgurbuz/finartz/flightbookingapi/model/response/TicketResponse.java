package com.hgurbuz.finartz.flightbookingapi.model.response;

import com.hgurbuz.finartz.flightbookingapi.converter.TicketConverter;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Ticket;
import lombok.Data;

@Data
public class TicketResponse {
    private Long id;
    private String creditCard;
    private int numOfSeats;
    private String passengerName;
    private String passengerSurname;
    private Long flightId;
    private float totalPrice;

    public static TicketResponse of(Ticket ticket) {
        return TicketConverter.convert(ticket);
    }
}
