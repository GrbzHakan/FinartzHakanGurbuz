package com.hgurbuz.finartz.flightbookingapi.controller;

import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.TicketRepository;
import com.hgurbuz.finartz.flightbookingapi.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse buyTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        return ticketService.buyTicket(ticketRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }


    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TicketResponse findTicketById(@PathVariable Long id) {
        return ticketService.findTicketById(id);
    }
}
