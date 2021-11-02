package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.request.FlightRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;

import java.util.List;

public interface FlightService {
    FlightResponse createFlight(FlightRequest flightRequest);
    List<FlightResponse> fetchAllFlights();
    FlightResponse updateFlight(Long id, FlightRequest flightRequest);
    FlightResponse findFlightById(Long id);
    Flight findFlightByIdAndReturnFlight(Long id);
    void deleteFlight(Long id);
    List<FlightResponse> findAllFlightsByRouteId(Long routeId);
    List<FlightResponse> findAllFlightsByCompanyId(Long id);
    void updateRemainCapacity(int remainCapacity, Long id);
}
