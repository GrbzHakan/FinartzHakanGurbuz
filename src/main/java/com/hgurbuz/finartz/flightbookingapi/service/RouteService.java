package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.RouteRepository;

import java.util.List;

public interface RouteService{
    RouteResponse addRoute(RouteRequest routeRequest);
    RouteResponse updateRoute(Long id, RouteRequest routeRequest);
    List<RouteResponse> findAllRoutes();
    RouteResponse findRouteById(Long id);
    void deleteRouteById(Long id);
}
