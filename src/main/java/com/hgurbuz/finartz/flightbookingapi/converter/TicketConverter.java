package com.hgurbuz.finartz.flightbookingapi.converter;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Ticket;
import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketConverter {
    public static TicketResponse convert(Ticket ticket) {
        var ticketResponse = new TicketResponse();
        ticketResponse.setId(ticket.getId());
        ticketResponse.setCreditCard(ticket.getCreditCard());
        ticketResponse.setFlightId(ticket.getFlight().getId());
        ticketResponse.setNumOfSeats(ticket.getNumOfSeats());
        ticketResponse.setPassengerName(ticket.getPassengerName());
        ticketResponse.setPassengerSurname(ticket.getPassengerSurname());
        ticketResponse.setTotalPrice(ticket.getTotalPrice());
        return ticketResponse;
    }

    public static Ticket convert(TicketRequest ticketRequest, Flight flight, float totalPrice , String formattedCCN) {
        var ticket = new Ticket();
        ticket.setCreditCard(formattedCCN);
        ticket.setNumOfSeats(ticketRequest.getNumOfSeats());
        ticket.setFlight(flight);
        ticket.setPassengerName(ticketRequest.getPassengerName());
        ticket.setPassengerSurname(ticketRequest.getPassengerSurname());
        ticket.setTotalPrice(totalPrice);
        return ticket;
    }
}
