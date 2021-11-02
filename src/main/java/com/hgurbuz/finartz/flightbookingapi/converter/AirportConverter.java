package com.hgurbuz.finartz.flightbookingapi.converter;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import org.springframework.stereotype.Component;

@Component
public class AirportConverter {
    public static AirportResponse convert(Airport airport) {
        var airportResponse = new AirportResponse();
        airportResponse.setId(airport.getId());
        airportResponse.setName(airport.getName());
        airportResponse.setCity(airport.getCity());
        return airportResponse;
    }

    public static Airport convert(AirportRequest airportRequest) {
        var airport = new Airport();
        airport.setName(airportRequest.getName());
        airport.setCity(airportRequest.getCity());
        return airport;
    }


    public static Airport convert(AirportResponse airportResponse) {
        var airport = new Airport();
        airport.setId(airportResponse.getId());
        airport.setName(airportResponse.getName());
        airport.setCity(airportResponse.getCity());
        return airport;
    }
}
