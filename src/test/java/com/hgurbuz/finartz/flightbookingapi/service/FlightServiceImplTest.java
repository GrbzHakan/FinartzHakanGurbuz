package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.CompanyConverter;
import com.hgurbuz.finartz.flightbookingapi.converter.RouteConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import com.hgurbuz.finartz.flightbookingapi.model.request.FlightRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {
    @InjectMocks
    FlightServiceImpl flightService;
    @Mock
    CompanyService companyService;
    @Mock
    RouteService routeService;
    @Mock
    FlightRepository flightRepository;

    @Test
    public void whenAddFlightValidRequest_itShouldSaveWithValidResponse(){

        CompanyResponse company = getCompanyResponse();
        RouteResponse route = getRouteResponse();
        Flight flight = getFlight(CompanyConverter.convert(company), RouteConverter.convert(route));
        FlightRequest flightRequest = getFlightRequest();
        FlightResponse flightResponse = getFlightResponse();

        Mockito.when(companyService.findCompanyById(flightRequest.getCompanyId())).thenReturn(company);
        Mockito.when(routeService.findRouteById(flightRequest.getRouteId())).thenReturn(route);
        Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(flight);

        FlightResponse result = flightService.createFlight(flightRequest);

        assertEquals(result.getId(),flightResponse.getId());
        assertEquals(result.getCompanyId(),flightResponse.getCompanyId());
        assertEquals(result.getMaxCapacity(),flightResponse.getMaxCapacity());
        assertEquals(result.getPrice(),flightResponse.getMaxCapacity());
        assertEquals(result.getRemainCapacity(),flightResponse.getRemainCapacity());
        assertEquals(result.getRouteId(),flightResponse.getRouteId());
        assertEquals(result.getPrice(),flightResponse.getPrice());

        Mockito.verify(companyService).findCompanyById(flightRequest.getCompanyId());
        Mockito.verify(routeService).findRouteById(flightRequest.getRouteId());
        Mockito.verify(flightRepository).save(Mockito.any(Flight.class));
    }

    @Test
    public void whenFetchAllFlights_itShouldReturnValidList(){
        List<Flight> flightList = getFlightList();
        List<FlightResponse> flightExpectedResponses = FlightResponse.of(flightList);

        Mockito.when(flightRepository.findAll()).thenReturn(flightList);

        List<FlightResponse> result = flightService.fetchAllFlights();

        assertEquals(flightExpectedResponses,result);

        Mockito.verify(flightRepository).findAll();

    }
    @Test
    public void givenIdToDeleteThen_itShouldDeleteTheFlight(){
        flightService.deleteFlight(1000L);
        Mockito.verify(flightRepository).deleteById(1000L);

    }

    @Test
    public void itShouldgivenIdAndCapacityToUpdate(){
        flightService.updateRemainCapacity(1000,12L);
        Mockito.verify(flightRepository).updateRemainCapacity(1000,12L);

    }

    @Test
    public void whenFindFlightWithValidId_itShouldReturnValidResponse(){
        Flight flight = getFlight
                (CompanyConverter.convert(getCompanyResponse())
                        ,RouteConverter.convert(getRouteResponse()));
        FlightResponse flightResponse = getFlightResponse();

        Mockito.when(flightRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(flight));
        FlightResponse result = flightService.findFlightById(flight.getId());

        assertEquals(result,flightResponse);

        Mockito.verify(flightRepository).findById(flight.getId());

    }

    @Test
    public void itShouldThrowNotFoundException_WhenThereAreNoAnyFlight() {
        Flight flight = getFlight
                (CompanyConverter.convert(getCompanyResponse())
                        ,RouteConverter.convert(getRouteResponse()));


        Mockito.when(flightRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(flightRepository.findFlightsByCompany_Id(Mockito.anyLong())).thenReturn(new ArrayList<>());

        NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
                        flightService.findFlightById(flight.getId()),
                NotFoundException.class);
        Assertions.assertThat(notFoundException).isNotNull();
        Assertions.assertThat(notFoundException.getMessage()).isEqualTo(ExceptionConstant.FLIGHT_NOT_FOUND_MESSAGE + flight.getId());

        Mockito.verify(flightRepository).findById(Mockito.any());

        NotFoundException notFoundException2 = Assertions.catchThrowableOfType(() ->
                        flightService.findAllFlightsByCompanyId(flight.getCompany().getId()),
                NotFoundException.class);
        Assertions.assertThat(notFoundException2).isNotNull();
        Assertions.assertThat(notFoundException2.getMessage()).isEqualTo(ExceptionConstant.COMPANY_FLIGHTS_NOT_FOUND_MESSAGE + flight.getCompany().getId());

        Mockito.verify(flightRepository).findFlightsByCompany_Id(Mockito.any());
    }

    @Test
    public void whenFindFlightWithValidCompanyId_itShouldReturnValidResponse(){

        List<Flight> flightList = getFlightList();
        List<FlightResponse> flightResponseList = getFlightResponseList();



        Mockito.when(flightRepository.findFlightsByCompany_Id(Mockito.anyLong())).thenReturn(flightList);
        List<FlightResponse> result = flightService.findAllFlightsByCompanyId(Mockito.anyLong());

        assertEquals(result,flightResponseList);



    }



    public Flight getFlight(Company company, Route route){
        Flight flight = new Flight();
        flight.setRemainCapacity(300);
        flight.setMaxCapacity(300);
        flight.setCompany(company);
        flight.setRoute(route);
        flight.setArriveTime(new Date());
        flight.setDepartureTime(new Date());
        flight.setId(1L);
        flight.setPrice(300.0F);
        flight.setTicket(new ArrayList<>());
        return flight;
    }
    public CompanyResponse getCompanyResponse(){
        CompanyResponse company = new CompanyResponse();
        company.setId(1L);
        company.setName("Turkish Airlines");
        return company;
    }
    public RouteResponse getRouteResponse(){
        RouteResponse route = new RouteResponse();
        route.setId(1L);
        route.setArrivalCity("İstanbul");
        route.setDepartureCity("Çanakkale");
        return route;
    }
    public FlightRequest getFlightRequest(){
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setArrivalTime(new Date());
        flightRequest.setDepartureTime(new Date());
        flightRequest.setCompanyId(1L);
        flightRequest.setRouteId(1L);
        flightRequest.setPrice(200.0f);
        flightRequest.setMaxCapacity(300);
        return flightRequest;
    }
    public FlightResponse getFlightResponse(){
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setRouteId(1L);
        flightResponse.setCompanyId(1L);
        flightResponse.setPrice(300.0f);
        flightResponse.setDepartureTime(new Date());
        flightResponse.setArrivalTime(new Date());
        flightResponse.setMaxCapacity(300);
        flightResponse.setRemainCapacity(300);
        flightResponse.setId(1L);
        return flightResponse;
    }

    public List<Flight> getFlightList(){
        Flight flight1 = new Flight();
        flight1.setRemainCapacity(300);
        flight1.setMaxCapacity(300);
        flight1.setCompany(new Company(1l,new ArrayList<>(),"company"));
        flight1.setRoute(new Route(1l,"route2","route",new ArrayList<>()));
        flight1.setArriveTime(new Date());
        flight1.setDepartureTime(new Date());
        flight1.setId(1L);
        flight1.setPrice(300.0F);
        flight1.setTicket(new ArrayList<>());

        Flight flight2 = new Flight();
        flight2.setRemainCapacity(300);
        flight2.setMaxCapacity(300);
        flight2.setCompany(new Company(1l,new ArrayList<>(),"company"));
        flight2.setRoute(new Route(1l,"route2","route",new ArrayList<>()));
        flight2.setArriveTime(new Date());
        flight2.setDepartureTime(new Date());
        flight2.setId(2L);
        flight2.setPrice(300.0F);
        flight2.setTicket(new ArrayList<>());
        return List.of(flight1,flight2);
    }

    public List<FlightResponse> getFlightResponseList(){
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setRouteId(1L);
        flightResponse.setCompanyId(1L);
        flightResponse.setPrice(300.0f);
        flightResponse.setDepartureTime(new Date());
        flightResponse.setArrivalTime(new Date());
        flightResponse.setMaxCapacity(300);
        flightResponse.setRemainCapacity(300);
        flightResponse.setId(1L);

        FlightResponse flightResponse2 = new FlightResponse();
        flightResponse2.setRouteId(1L);
        flightResponse2.setCompanyId(1L);
        flightResponse2.setPrice(300.0f);
        flightResponse2.setDepartureTime(new Date());
        flightResponse2.setArrivalTime(new Date());
        flightResponse2.setMaxCapacity(300);
        flightResponse2.setRemainCapacity(300);
        flightResponse2.setId(2L);
        return List.of(flightResponse,flightResponse2);

    }
}
