package com.hgurbuz.finartz.flightbookingapi.utils;

import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RouteUtilTest {
    @InjectMocks
    RouteUtil routeUtil;

    @Mock
    AirportRepository airportRepository;

    @Test
    void isAirportExistFromRouteRequest() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setArrivalCity("Çanakkale");
        routeRequest.setDepartureCity("İstanbul");

        Mockito.when(airportRepository.existsAirportsByCity(Mockito.anyString())).thenReturn(true);

        Boolean result = routeUtil.isAirportExistFromRouteRequest(routeRequest);

        assertTrue(result);

        Mockito.verify(airportRepository,Mockito.times(2)).existsAirportsByCity(Mockito.anyString());

    }
}