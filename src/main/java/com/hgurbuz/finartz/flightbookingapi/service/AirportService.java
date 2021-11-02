package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse findAirportById(Long id);
    List<AirportResponse> findAllAirports();
    AirportResponse addAirport(AirportRequest airportRequest);
    AirportResponse updateAirport(Long id, AirportRequest airportRequest);
    void deleteAirportById(Long id);

}
