package com.hgurbuz.finartz.flightbookingapi.controller;

import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/airports")
public class AirportController {
    private final AirportService airportService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AirportResponse addAirport(@Valid @RequestBody AirportRequest request){return airportService.addAirport(request);}

    @GetMapping
    public List<AirportResponse> fetchAllAirports() {
        return airportService.findAllAirports();
    }

    @GetMapping("/{id}")
    public AirportResponse findAirportById(@PathVariable Long id){
        return airportService.findAirportById(id);
    }

    @PutMapping("/{id}")
    public AirportResponse updateAirportById(@PathVariable Long id,@Valid @RequestBody AirportRequest request){
        return airportService.updateAirport(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteAirportById(@PathVariable Long id){
        airportService.deleteAirportById(id);
    }
}
