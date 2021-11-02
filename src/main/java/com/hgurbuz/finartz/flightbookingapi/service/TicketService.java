package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;

public interface TicketService {
    TicketResponse buyTicket(TicketRequest ticketRequest);
    void deleteTicket(Long id);
    TicketResponse findTicketById(Long id);
}
