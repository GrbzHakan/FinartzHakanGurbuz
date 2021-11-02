package com.hgurbuz.finartz.flightbookingapi.converter;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import com.hgurbuz.finartz.flightbookingapi.model.request.FlightRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FlightConverter {
    public static FlightResponse convert(Flight flight) {
        var flightResponse = new FlightResponse();
        flightResponse.setId(flight.getId());
        flightResponse.setArrivalTime(flight.getArriveTime());
        flightResponse.setCompanyId(flight.getCompany().getId());
        flightResponse.setDepartureTime(flight.getDepartureTime());
        flightResponse.setMaxCapacity(flight.getMaxCapacity());
        flightResponse.setRouteId(flight.getRoute().getId());
        flightResponse.setPrice(flight.getPrice());
        flightResponse.setRemainCapacity(flight.getRemainCapacity());
        return flightResponse;
    }

    public static Flight convert(FlightRequest flightRequest, Company company,Route route) {
        var flight = new Flight();
        flight.setCompany(company);
        flight.setRoute(route);
        flight.setArriveTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setMaxCapacity(flightRequest.getMaxCapacity());
        flight.setRemainCapacity(flightRequest.getMaxCapacity());
        flight.setPrice(flightRequest.getPrice());
        flight.setTicket(new ArrayList<>());
        return flight;
    }
    public static Flight convert(FlightRequest flightRequest, Company company,Route route, Flight flight) {
        flight.setCompany(company);
        flight.setRoute(route);
        flight.setArriveTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setMaxCapacity(flightRequest.getMaxCapacity());
        flight.setPrice(flightRequest.getPrice());
        return flight;
    }





}
