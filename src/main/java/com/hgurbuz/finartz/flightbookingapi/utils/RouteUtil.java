package com.hgurbuz.finartz.flightbookingapi.utils;

import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteUtil {
    @Autowired
    private AirportRepository airportRepository;
    public  boolean isAirportExistFromRouteRequest(RouteRequest request){
        var departureCity = request.getDepartureCity();
        var arrivalCity = request.getArrivalCity();
        return (airportRepository.existsAirportsByCity(departureCity)
                && airportRepository.existsAirportsByCity(arrivalCity));
    }

}
