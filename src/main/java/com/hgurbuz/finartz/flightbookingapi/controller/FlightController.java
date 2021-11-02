package com.hgurbuz.finartz.flightbookingapi.controller;

import com.hgurbuz.finartz.flightbookingapi.model.request.FlightRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/flights")
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public FlightResponse createFlight(@RequestBody @Valid FlightRequest flightRequest){
        return flightService.createFlight(flightRequest);
    }

    @GetMapping
    public List<FlightResponse> fetchAllFlights() {return flightService.fetchAllFlights();}

    @PutMapping("/{id}")
    public FlightResponse updateFlight(@PathVariable Long id,@Valid @RequestBody FlightRequest flightRequest) {
        return flightService.updateFlight(id,flightRequest);
    }

    @GetMapping("/{id}")
    public FlightResponse findFlightById(@PathVariable Long id){
        return flightService.findFlightById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id){flightService.deleteFlight(id);}

    @GetMapping("/route/{routeId}")
    public List<FlightResponse> findFlightsByRouteId(@PathVariable Long routeId){
        return flightService.findAllFlightsByRouteId(routeId);
    }



}
