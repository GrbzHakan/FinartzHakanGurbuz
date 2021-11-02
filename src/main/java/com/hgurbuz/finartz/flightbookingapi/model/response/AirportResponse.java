package com.hgurbuz.finartz.flightbookingapi.model.response;

import com.hgurbuz.finartz.flightbookingapi.converter.AirportConverter;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AirportResponse {
    private Long id;
    private String name;
    private String city;

    public static List<AirportResponse> of(List<Airport> airportList) {
        return airportList.stream()
                .map(AirportConverter::convert).collect(Collectors.toList());
    }
    public static AirportResponse of(Airport airport) {
        return AirportConverter.convert(airport);
    }

}
