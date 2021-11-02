package com.hgurbuz.finartz.flightbookingapi.converter;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RouteConverter {
    public static RouteResponse convert(Route route) {
        var routeResponse = new RouteResponse();
        routeResponse.setArrivalCity(route.getArrivalCity());
        routeResponse.setDepartureCity(route.getDepartureCity());
        routeResponse.setId(route.getId());
        return routeResponse;
    }

    public static Route convert(RouteRequest routeRequest) {
        var route = new Route();
        route.setArrivalCity(routeRequest.getArrivalCity());
        route.setDepartureCity(routeRequest.getDepartureCity());
        route.setFlightList(new ArrayList<>());
        return route;
    }


    public static Route convert(RouteResponse routeResponse) {
        var route = new Route();
        route.setId(routeResponse.getId());
        route.setDepartureCity(routeResponse.getDepartureCity());
        route.setArrivalCity(routeResponse.getArrivalCity());
        return route;
    }
}
