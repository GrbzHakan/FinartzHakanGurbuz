package com.hgurbuz.finartz.flightbookingapi.utils;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.exception.SeatCapacityExceedException;
import com.hgurbuz.finartz.flightbookingapi.model.request.TicketRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.TicketResponse;
import com.hgurbuz.finartz.flightbookingapi.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RequiredArgsConstructor
@Component
public class TicketUtil {

    private static final String CREDIT_CARD_FORMAT_PATTERN="-?[^\\d]";
    private final FlightService flightService;


    private float calculatePrice(int maxCapacity,int remainCapacity,int numberOfSeat, float perSeatPrice){
        var capacityPercentege =100 - remainCapacity * 100 / maxCapacity;
        var percantegeFactor = capacityPercentege / 10;
        if(percantegeFactor > 1){
            var tenPercentOfSeatPrice = perSeatPrice/10;
            var increasedSeatPrice = tenPercentOfSeatPrice * percantegeFactor + perSeatPrice;
            return increasedSeatPrice * numberOfSeat;
        }else{

            return perSeatPrice * numberOfSeat;

        }

    }

    public float checkSeatCapacityAndReturnTotalPrice(TicketRequest ticketRequest){
        var flightResponse = flightService.findFlightById(ticketRequest.getFlightId());
        var remainCapacity = flightResponse.getRemainCapacity();
        var maxCapacity = flightResponse.getMaxCapacity();
        var numberOfSeats = ticketRequest.getNumOfSeats();
        if(numberOfSeats > maxCapacity || numberOfSeats > remainCapacity){
            throw new SeatCapacityExceedException(ExceptionConstant.SEAT_CAPACITY_EXCEED_MESSAGE);
        }else{
            remainCapacity = remainCapacity - ticketRequest.getNumOfSeats();
            flightService.updateRemainCapacity(remainCapacity,flightResponse.getId());
            return calculatePrice(maxCapacity,remainCapacity,ticketRequest.getNumOfSeats(),flightResponse.getPrice());
        }
    }

    private String checkCreditCardFormat(String creditCard){
        Pattern regex = Pattern.compile(CREDIT_CARD_FORMAT_PATTERN);
        Matcher regexMatcher = regex.matcher(creditCard);
        if(regexMatcher.find()){
            return  creditCard.replaceAll(CREDIT_CARD_FORMAT_PATTERN, "");
        }
        return creditCard;
    }

    public String maskCreditCard(String creditCard){
        String strippedCreditCard = checkCreditCardFormat(creditCard);
        var subSectionOfCreditCard = strippedCreditCard.substring(6, strippedCreditCard.length() - 4);
        var prefix = strippedCreditCard.substring(0, 6);
        var middle = String.join("", Collections.nCopies(subSectionOfCreditCard.length(), "#"));
        var suffix = strippedCreditCard.substring(strippedCreditCard.length() - 4);
        return prefix + middle + suffix;
    }

    public void whenDeleteTicketReAddSeat(TicketResponse ticketResponse){
        var flightResponse = flightService.findFlightById(ticketResponse.getFlightId());
        var remainCapacity = flightResponse.getRemainCapacity();
        var reAddedRemainCapacity = remainCapacity + ticketResponse.getNumOfSeats();
        flightService.updateRemainCapacity(reAddedRemainCapacity,flightResponse.getId());
    }

}
