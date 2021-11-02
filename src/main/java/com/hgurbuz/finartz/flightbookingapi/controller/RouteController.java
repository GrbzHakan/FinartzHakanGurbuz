package com.hgurbuz.finartz.flightbookingapi.controller;

import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import com.hgurbuz.finartz.flightbookingapi.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/routes")
public class RouteController {
    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RouteResponse addRoute(@Valid @RequestBody RouteRequest routeRequest){
        return routeService.addRoute(routeRequest);
    }

    @GetMapping
    public List<RouteResponse> fetchAllRoutes(){
        return routeService.findAllRoutes();
    }

    @GetMapping("/{id}")
    public RouteResponse findRouteById(@PathVariable Long id){
        return routeService.findRouteById(id);
    }

    @PutMapping("/{id}")
    public RouteResponse updateRouteById(@PathVariable Long id,@Valid @RequestBody RouteRequest routeRequest){
        return routeService.updateRoute(id,routeRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id){
        routeService.deleteRouteById(id);
    }


}
