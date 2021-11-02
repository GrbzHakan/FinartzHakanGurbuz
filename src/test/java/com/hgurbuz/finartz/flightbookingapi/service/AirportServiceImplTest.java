package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.converter.AirportConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.AirportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {

    @Mock
    AirportRepository airportRepository;

    @InjectMocks
    AirportServiceImpl airportService;

    @Test
    public void whenAddAirportCalledWithValidRequest_itShouldReturnValidResponse(){
        AirportRequest airportRequest = new AirportRequest();
        airportRequest.setCity("İstanbul");
        airportRequest.setName("İstanbul Havaalanı");

        Airport airport = new Airport();

        airport.setName("İstanbul Havaalanı");
        airport.setCity("İstanbul");

        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setCity("İstanbul");
        airportResponse.setName("İstanbul Havaalanı");

        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(airport);


        AirportResponse result = airportService.addAirport(airportRequest);

        assertEquals(result,airportResponse);
        Mockito.verify(airportRepository).save(airport);

    }

    @Test
    public void whenFindAirportWithValidId_itShouldReturnValidResponse(){
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setName("İstanbul Havaalanı");
        airport.setCity("İstanbul");

        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setId(1L);
        airportResponse.setCity("İstanbul");
        airportResponse.setName("İstanbul Havaalanı");

        Mockito.when(airportRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(airport));
        AirportResponse result = airportService.findAirportById(airport.getId());

        Mockito.verify(airportRepository).findById(airport.getId());
        assertEquals(result,airportResponse);
    }

    @Test
    public void itShouldFindAllAirports(){
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setCity("İstanbul Havaalanı");
        airport.setName("İstanbul");

        Airport airport1 = new Airport();
        airport1.setId(3L);
        airport1.setCity("Çorum Havaalanı");
        airport1.setName("Çorum");

        Airport airport2 = new Airport();
        airport2.setId(2L);
        airport2.setCity("Çanakkale Havaalanı");
        airport2.setName("Çanakkale");


        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setId(1L);
        airportResponse.setCity("İstanbul");
        airportResponse.setName("İstanbul Havaalanı");

        AirportResponse airportResponse1 = new AirportResponse();
        airportResponse1.setId(1L);
        airportResponse1.setCity("İstanbul");
        airportResponse1.setName("İstanbul Havaalanı");

        AirportResponse airportResponse2 = new AirportResponse();
        airportResponse2.setId(1L);
        airportResponse2.setCity("İstanbul");
        airportResponse2.setName("İstanbul Havaalanı");

        List<Airport> airportList = List.of(airport1,airport2,airport);

        Mockito.when(airportRepository.findAll()).thenReturn(airportList);

        List<AirportResponse> airportResponseList = AirportResponse.of(airportList);

        List<AirportResponse> result = airportService.findAllAirports();

        Mockito.verify(airportRepository).findAll();

        assertEquals(result,airportResponseList);
    }

    @Test
    public void itShouldThrowNotFoundException_WhenThereAreNoAnyAirport() {
        Airport airport = new Airport();
        airport.setId(1L);
        airport.setName("İstanbul Havaalanı");
        airport.setCity("İstanbul");
        Mockito.when(airportRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
                        airportService.findAirportById(airport.getId()),
                NotFoundException.class);

        Mockito.verify(airportRepository).findById(airport.getId());
        Assertions.assertThat(notFoundException).isNotNull();
        Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Airport not found with the given id " + airport.getId());
    }

    @Test
    public void whenUpdateAirportWithValidIdAndRequest_itShouldReturnValidResponse(){
        AirportRequest airportRequest = new AirportRequest();
        airportRequest.setCity("İstanbul");
        airportRequest.setName("İstanbul Havaalanı");



        Airport optionalAirport = new Airport();
        optionalAirport.setId(1L);
        optionalAirport.setCity("Çanakkale");
        optionalAirport.setName("Çanakkale Havaalanı");

        AirportResponse airportExpectedResponse = new AirportResponse();
        airportExpectedResponse.setId(1L);
        airportExpectedResponse.setCity("İstanbul");
        airportExpectedResponse.setName("İstanbul Havaalanı");

        Airport airportConverted = new Airport();
        airportConverted.setId(1L);
        airportConverted.setCity("İstanbul");
        airportConverted.setName("İstanbul Havaalanı");


        Mockito.when(airportRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(optionalAirport));;
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(airportConverted);

        AirportResponse result = airportService.updateAirport(1L,airportRequest);

        assertEquals(airportExpectedResponse,result);

        Mockito.verify(airportRepository).findById(Mockito.anyLong());
        Mockito.verify(airportRepository).save(Mockito.any(Airport.class));


    }

    @Test
    public void givenIdToDeleteThen_itShouldDeleteTheAirport(){
          airportService.deleteAirportById(1000L);
        Mockito.verify(airportRepository).deleteById(1000L);

    }
}