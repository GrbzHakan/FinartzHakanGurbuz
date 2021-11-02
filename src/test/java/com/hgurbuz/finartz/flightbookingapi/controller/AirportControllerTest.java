package com.hgurbuz.finartz.flightbookingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.service.AirportService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AirportController.class)
class AirportControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AirportService airportService;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void itShouldAddAirport() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/airports");
        AirportResponse airportResponse = getAirportResponse();
        AirportRequest airportRequest = getAirportRequest();

        Mockito.when(airportService.addAirport(Mockito.any())).thenReturn(airportResponse);

        mockMvc.perform(post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(airportRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airportResponse.getId()))
                .andExpect(jsonPath("$.name").value(airportResponse.getName()))
                .andExpect(jsonPath("$.city").value(airportResponse.getCity()))
                .andReturn();


    }

    @Test
    public void itShouldFetchAllAirports() throws  Exception{
        List<AirportResponse> airportResponseList = List.of(getAirportResponse());
        final String endpoint = String.format("/api/airports");

        Mockito.when(airportService.findAllAirports()).thenReturn(airportResponseList);

        mockMvc.perform(get(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(airportResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(airportResponseList.get(0).getName()))
                .andExpect(jsonPath("$[0].city").value(airportResponseList.get(0).getCity()))
                .andReturn();
    }

    @Test
    public void itShouldFindAirportById() throws Exception{

        final String endpoint = String.format("/api/airports/{id}");
        AirportResponse airportResponse = getAirportResponse();
        Long id = 1L;

        Mockito.when(airportService.findAirportById(Mockito.any())).thenReturn(airportResponse);

        mockMvc.perform(get(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airportResponse.getId()))
                .andExpect(jsonPath("$.name").value(airportResponse.getName()))
                .andExpect(jsonPath("$.city").value(airportResponse.getCity()))
                .andReturn();


    }

    @Test
    public void itShouldUpdateAirportById() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/airports/{id}");
        AirportResponse airportResponse = getAirportResponse();
        AirportRequest airportRequest = getAirportRequest();
        Long id = 1L;

        Mockito.when(airportService.updateAirport(Mockito.any(),Mockito.any())).thenReturn(airportResponse);

        mockMvc.perform(put(endpoint,id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(airportRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(airportResponse.getId()))
                .andExpect(jsonPath("$.name").value(airportResponse.getName()))
                .andExpect(jsonPath("$.city").value(airportResponse.getCity()))
                .andReturn();

    }
    @Test
    public void itShoulDeleteAirportById() throws Exception{
        final String endpoint = String.format("/api/airports/{id}");
        Long id = 1L;
        mockMvc.perform(delete(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public AirportResponse getAirportResponse(){
        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setId(1L);
        airportResponse.setName("Sabiha Gökçen");
        airportResponse.setCity("İstanbul");
        return airportResponse;
    }
    public AirportRequest getAirportRequest(){
        AirportRequest request = new AirportRequest();
        request.setName("Sabiha Gökçen");
        request.setCity("İstanbul");
        return  request;
    }

}