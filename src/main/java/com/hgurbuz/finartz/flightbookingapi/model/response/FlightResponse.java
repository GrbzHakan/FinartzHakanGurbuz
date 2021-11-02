package com.hgurbuz.finartz.flightbookingapi.model.response;

import com.hgurbuz.finartz.flightbookingapi.converter.FlightConverter;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FlightResponse {
    private Long id;
    private Long companyId;
    private Long routeId;
    private int maxCapacity;
    private float price;
    private Date departureTime;
    private Date arrivalTime;
    private int remainCapacity;

    public static List<FlightResponse> of(List<Flight> flightList) {
        return flightList.stream()
                .map(FlightConverter::convert).collect(Collectors.toList());
    }
    public static FlightResponse of(Flight flight) {
        return FlightConverter.convert(flight);
    }
}
