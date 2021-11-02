package com.hgurbuz.finartz.flightbookingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.service.CompanyService;
import com.hgurbuz.finartz.flightbookingapi.service.FlightService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CompanyService companyService;
    @MockBean
    FlightService flightService;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }



    @Test
    public void itShouldAddCompany() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/companies");
        CompanyResponse companyResponse = getCompanyResponse();
        CompanyRequest companyRequest = getCompanyRequest();

        Mockito.when(companyService.addCompany(Mockito.any())).thenReturn(companyResponse);

        mockMvc.perform(post(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(companyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(companyResponse.getId()))
                .andExpect(jsonPath("$.name").value(companyResponse.getName()))
                .andReturn();


    }

    @Test
    public void itShouldFetchAllCompanies() throws  Exception{
        List<CompanyResponse> companyResponseList = List.of(getCompanyResponse());
        final String endpoint = String.format("/api/companies");

        Mockito.when(companyService.findAllCompanies()).thenReturn(companyResponseList);

        mockMvc.perform(get(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(companyResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(companyResponseList.get(0).getName()))
                .andReturn();
    }

    @Test
    public void itShouldFindCompanyById() throws Exception{

        final String endpoint = String.format("/api/companies/{id}");
        CompanyResponse companyResponse = getCompanyResponse();
        Long id = 1L;

        Mockito.when(companyService.findCompanyById(Mockito.any())).thenReturn(companyResponse);

        mockMvc.perform(get(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(companyResponse.getId()))
                .andExpect(jsonPath("$.name").value(companyResponse.getName()))
                .andReturn();


    }

    @Test
    public void itShouldUpdateAirportById() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        final String endpoint = String.format("/api/companies/{id}");
        CompanyResponse companyResponse = getCompanyResponse();
        CompanyRequest companyRequest = getCompanyRequest();
        Long id = 1L;

        Mockito.when(companyService.updateCompanyById(Mockito.any(),Mockito.any())).thenReturn(companyResponse);

        mockMvc.perform(put(endpoint,id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(companyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(companyResponse.getId()))
                .andExpect(jsonPath("$.name").value(companyResponse.getName()))
                .andReturn();

    }
    @Test
    public void itShoulDeleteAirportById() throws Exception{
        final String endpoint = String.format("/api/companies/{id}");
        Long id = 1L;
        mockMvc.perform(delete(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void itShoulGetAllFlightsOfCompany() throws Exception{
        final String endpoint = String.format("/api/companies/flights/{id}");
        Long id = 1L;
        mockMvc.perform(get(endpoint, id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public CompanyRequest getCompanyRequest(){
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setName("Pegasus");
        return companyRequest;
    }

    public CompanyResponse getCompanyResponse(){
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(1L);
        companyResponse.setName("Pegasus");
        return companyResponse;
    }


}