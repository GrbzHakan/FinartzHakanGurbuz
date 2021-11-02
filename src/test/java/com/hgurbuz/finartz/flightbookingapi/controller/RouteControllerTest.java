package com.hgurbuz.finartz.flightbookingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import com.hgurbuz.finartz.flightbookingapi.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RouteController.class)
class RouteControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RouteService routeService;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void itShouldAddRoute() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/routes");
        RouteResponse routeResponse = getRouteResponse();
        RouteRequest routeRequest = getRouteRequest();

        Mockito.when(routeService.addRoute(Mockito.any())).thenReturn(routeResponse);

        mockMvc.perform(post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(routeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routeResponse.getId()))
                .andExpect(jsonPath("$.arrivalCity").value(routeResponse.getArrivalCity()))
                .andExpect(jsonPath("$.departureCity").value(routeResponse.getDepartureCity()))
                .andReturn();


    }

    @Test
    public void itShouldFetchAllRoutes() throws  Exception{
        List<RouteResponse> routeResponseList = List.of(getRouteResponse());
        final String endpoint = String.format("/api/routes");

        Mockito.when(routeService.findAllRoutes()).thenReturn(routeResponseList);

        mockMvc.perform(get(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(routeResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].arrivalCity").value(routeResponseList.get(0).getArrivalCity()))
                .andExpect(jsonPath("$[0].departureCity").value(routeResponseList.get(0).getDepartureCity()))
                .andReturn();
    }

    @Test
    public void itShouldFindRouteById() throws Exception{

        final String endpoint = String.format("/api/routes/{id}");
        RouteResponse routeResponse = getRouteResponse();
        Long id = 1L;

        Mockito.when(routeService.findRouteById(Mockito.any())).thenReturn(routeResponse);

        mockMvc.perform(get(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routeResponse.getId()))
                .andExpect(jsonPath("$.arrivalCity").value(routeResponse.getArrivalCity()))
                .andExpect(jsonPath("$.departureCity").value(routeResponse.getDepartureCity()))
                .andReturn();


    }

    @Test
    public void itShouldUpdateRouteById() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/routes/{id}");
        RouteResponse routeResponse = getRouteResponse();
        RouteRequest routeRequest = getRouteRequest();
        Long id = 1L;

        Mockito.when(routeService.updateRoute(Mockito.any(),Mockito.any())).thenReturn(routeResponse);

        mockMvc.perform(put(endpoint,id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(routeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(routeResponse.getId()))
                .andExpect(jsonPath("$.arrivalCity").value(routeResponse.getArrivalCity()))
                .andExpect(jsonPath("$.departureCity").value(routeResponse.getDepartureCity()))
                .andReturn();

    }
    @Test
    public void itShoulDeleteRouteById() throws Exception{
        final String endpoint = String.format("/api/routes/{id}");
        Long id = 1L;
        mockMvc.perform(delete(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public RouteRequest getRouteRequest(){
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("İstanbul");
        routeRequest.setArrivalCity("Ankara");
        return routeRequest;
    }

    public RouteResponse getRouteResponse(){
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setArrivalCity("Ankara");
        routeResponse.setDepartureCity("İstanbul");
        routeResponse.setId(1L);
        return routeResponse;
    }

}