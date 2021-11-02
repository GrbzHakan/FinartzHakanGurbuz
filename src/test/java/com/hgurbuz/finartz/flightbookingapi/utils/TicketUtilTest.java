package com.hgurbuz.finartz.flightbookingapi.utils;

import com.hgurbuz.finartz.flightbookingapi.exception.SeatCapacityExceedException;
import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;
import com.hgurbuz.finartz.flightbookingapi.service.FlightService;
import org.assertj.core.api.Assertions;
import org.hibernate.boot.model.relational.SimpleAuxiliaryDatabaseObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TicketUtilTest {

    @InjectMocks
    TicketUtil ticketUtil;

    @Mock
    FlightService flightService;

    @Test
    public void itShouldCheckSeatCapacityAndReturnTotalPrice(){
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setFlightId(1L);
        ticketRequest.setCreditCard("123-4567891233-456");
        ticketRequest.setNumOfSeats(100);
        ticketRequest.setPassengerName("Hakan");
        ticketRequest.setPassengerSurname("Gürbüz");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(1L);
        flightResponse.setRemainCapacity(300);
        flightResponse.setMaxCapacity(300);
        flightResponse.setArrivalTime(new Date());
        flightResponse.setDepartureTime(new Date());
        flightResponse.setCompanyId(1L);
        flightResponse.setPrice(300);
        flightResponse.setRouteId(1L);

        Mockito.when(flightService.findFlightById(Mockito.anyLong())).thenReturn(flightResponse);

        var result = ticketUtil.checkSeatCapacityAndReturnTotalPrice(ticketRequest);
        assertEquals(39000.0,result);
        Mockito.verify(flightService).findFlightById(Mockito.anyLong());

        ticketRequest.setNumOfSeats(5);
        var result2 = ticketUtil.checkSeatCapacityAndReturnTotalPrice(ticketRequest);
        assertEquals(1500.0,result2);



    }

    @Test
    public void itShouldCheckSeatCapacityAndThrowSeatCapacityExceedException(){
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setFlightId(1L);
        ticketRequest.setCreditCard("123-4567891233-456");
        ticketRequest.setNumOfSeats(350);
        ticketRequest.setPassengerName("Hakan");
        ticketRequest.setPassengerSurname("Gürbüz");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(1L);
        flightResponse.setRemainCapacity(300);
        flightResponse.setMaxCapacity(300);
        flightResponse.setArrivalTime(new Date());
        flightResponse.setDepartureTime(new Date());
        flightResponse.setCompanyId(1L);
        flightResponse.setPrice(300);
        flightResponse.setRouteId(1L);

        Mockito.when(flightService.findFlightById(Mockito.anyLong())).thenReturn(flightResponse);

        SeatCapacityExceedException seatCapacityExceedException = Assertions.catchThrowableOfType(() ->
                ticketUtil.checkSeatCapacityAndReturnTotalPrice(ticketRequest),
                SeatCapacityExceedException.class);
        Mockito.verify(flightService).findFlightById(Mockito.anyLong());
        Assertions.assertThat(seatCapacityExceedException).isNotNull();
        Assertions.assertThat(seatCapacityExceedException.getMessage()).isEqualTo("You cannot buy ticket because seat capacity exceed");

    }

    @Test
    public void itShouldCheckCreditCardFormatAndReturnMaskedFormat(){
        String creditCard1= "123-45678912-33456";
        String creditCard2 = "1234567891233456";

        String maskedCard1 = ticketUtil.maskCreditCard(creditCard1);
        String maskedCard2 = ticketUtil.maskCreditCard(creditCard2);

        assertEquals("123456######3456",maskedCard1);
        assertEquals("123456######3456",maskedCard2);
    }

    @Test
    public void itShoulReAddSeatWhenDeleteTicket(){
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setFlightId(1L);
        ticketResponse.setCreditCard("123-4567891233-456");
        ticketResponse.setNumOfSeats(100);
        ticketResponse.setPassengerName("Hakan");
        ticketResponse.setPassengerSurname("Gürbüz");

        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(1L);
        flightResponse.setRemainCapacity(200);
        flightResponse.setMaxCapacity(300);
        flightResponse.setArrivalTime(new Date());
        flightResponse.setDepartureTime(new Date());
        flightResponse.setCompanyId(1L);
        flightResponse.setPrice(300);
        flightResponse.setRouteId(1L);

        Mockito.when(flightService.findFlightById(Mockito.anyLong())).thenReturn(flightResponse);
        ticketUtil.whenDeleteTicketReAddSeat(ticketResponse);
        Mockito.verify(flightService).findFlightById(Mockito.anyLong());
        Mockito.verify(flightService).updateRemainCapacity(Mockito.anyInt(),Mockito.anyLong());

    }

}