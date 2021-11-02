package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.CompanyConverter;
import com.hgurbuz.finartz.flightbookingapi.converter.FlightConverter;
import com.hgurbuz.finartz.flightbookingapi.converter.RouteConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.request.FlightRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final CompanyService companyService;
    private final RouteService routeService;
    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(FlightRequest flightRequest) {
        var company = CompanyConverter.convert(companyService.findCompanyById(flightRequest.getCompanyId()));
        var route = RouteConverter.convert(routeService.findRouteById(flightRequest.getRouteId()));
        var flight = FlightConverter.convert(flightRequest,company,route);
        return FlightResponse.of(flightRepository.save(flight));
    }

    @Override
    public List<FlightResponse> fetchAllFlights() {
        return FlightResponse.of(flightRepository.findAll());
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        var optionalFlight = flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            var company = CompanyConverter.convert(companyService.findCompanyById(flightRequest.getCompanyId()));
            var route = RouteConverter.convert(routeService.findRouteById(flightRequest.getRouteId()));
            var updatedFlight = FlightConverter.convert(flightRequest,company,route,optionalFlight.get());
            return FlightResponse.of(flightRepository.save(updatedFlight));
        }else{
            throw new NotFoundException(ExceptionConstant.FLIGHT_NOT_FOUND_MESSAGE + id);
        }



    }

    @Override
    public void deleteFlight(Long id) {
         flightRepository.deleteById(id);
    }

    @Override
    public List<FlightResponse> findAllFlightsByRouteId(Long routeId) {
        return FlightResponse.of(flightRepository.findFlightsByRoute_Id(routeId));
    }

    @Override
    public FlightResponse findFlightById(Long id) {
        var optionalFlight = flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            return FlightResponse.of(optionalFlight.get());
        }else{
            throw new NotFoundException(ExceptionConstant.FLIGHT_NOT_FOUND_MESSAGE + id);
        }
    }

    @Override
    public List<FlightResponse> findAllFlightsByCompanyId(Long id) {
        List<FlightResponse> flightResponseList = FlightResponse.of(flightRepository.findFlightsByCompany_Id(id));
        if(flightResponseList.isEmpty()){
            throw new NotFoundException(ExceptionConstant.COMPANY_FLIGHTS_NOT_FOUND_MESSAGE + id);
        }else{
            return flightResponseList;
        }

    }

    @Override
    public void updateRemainCapacity(int remainCapacity, Long id) {
        flightRepository.updateRemainCapacity(remainCapacity,id);
    }

    @Override
    public Flight findFlightByIdAndReturnFlight(Long id) {
        var optionalFlight = flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            return optionalFlight.get();
        }else{
            throw new NotFoundException(ExceptionConstant.FLIGHT_NOT_FOUND_MESSAGE + id);
        }
    }
}
