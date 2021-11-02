package com.hgurbuz.finartz.flightbookingapi.model.response;

import com.hgurbuz.finartz.flightbookingapi.converter.RouteConverter;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RouteResponse {
    private Long id;
    private String arrivalCity;
    private String departureCity;

    public static List<RouteResponse> of(List<Route> routeList) {
        return routeList.stream()
                .map(RouteConverter::convert).collect(Collectors.toList());
    }
    public static RouteResponse of(Route route) {
        return RouteConverter.convert(route);
    }
}
