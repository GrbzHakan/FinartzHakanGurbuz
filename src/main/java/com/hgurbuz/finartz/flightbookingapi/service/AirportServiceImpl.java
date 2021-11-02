package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.AirportConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;

    @Override
    public AirportResponse findAirportById(Long id) {
        var airport = airportRepository.findById(id);
        if(airport.isPresent()){
            return AirportResponse.of(airport.get());
        }else{
            throw new NotFoundException(ExceptionConstant.AIRPORT_NOT_FOUND_MESSAGE + id);
        }
    }

    @Override
    public List<AirportResponse> findAllAirports() {
        return AirportResponse.of(airportRepository.findAll());
    }

    @Override
    public AirportResponse addAirport(AirportRequest airportRequest) {
        Airport airport = AirportConverter.convert(airportRequest);
        return AirportResponse.of(airportRepository.save(airport));
    }

    @Override
    public AirportResponse updateAirport(Long id,AirportRequest airportRequest) {
        AirportResponse airportResponse = findAirportById(id);
        airportResponse.setCity(airportRequest.getCity());
        airportResponse.setName(airportRequest.getName());
        return AirportResponse.of(airportRepository.save(AirportConverter.convert(airportResponse)));
    }

    @Override
    public void deleteAirportById(Long id) {
        airportRepository.deleteById(id);

    }
}
