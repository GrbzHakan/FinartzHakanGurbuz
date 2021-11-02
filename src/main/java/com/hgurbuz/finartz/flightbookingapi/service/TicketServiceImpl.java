package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.TicketConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.FlightRepository;
import com.hgurbuz.finartz.flightbookingapi.repository.TicketRepository;
import com.hgurbuz.finartz.flightbookingapi.utils.TicketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final TicketUtil ticketUtil;
    @Override

    public TicketResponse buyTicket(TicketRequest ticketRequest) {
       float totalPrice = ticketUtil.checkSeatCapacityAndReturnTotalPrice(ticketRequest);
       var flight = flightRepository.findById(ticketRequest.getFlightId());
       var formattedCCN = ticketUtil.maskCreditCard(ticketRequest.getCreditCard());
       if(flight.isPresent()) {
           return TicketResponse.of
                   (ticketRepository.save
                           (TicketConverter.convert(ticketRequest, flight.get(),totalPrice,formattedCCN )));
       }else{
           throw new NotFoundException(ExceptionConstant.FLIGHT_NOT_FOUND_MESSAGE + ticketRequest.getFlightId());
       }

    }

    @Override
    public void deleteTicket(Long id) {
        var ticketResponse = findTicketById(id);
        ticketUtil.whenDeleteTicketReAddSeat(ticketResponse);
        ticketRepository.deleteById(id);
    }

    @Override
    public TicketResponse findTicketById(Long id) {
        var optionalTicket = ticketRepository.findById(id);
        if(optionalTicket.isPresent()){
            return TicketResponse.of(optionalTicket.get());
        }else{
            throw new NotFoundException(ExceptionConstant.TICKET_NOT_FOUND_MESSAGE + id);
        }
    }
}
