package com.hgurbuz.finartz.flightbookingapi.controller;

import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.FlightResponse;
import com.hgurbuz.finartz.flightbookingapi.service.CompanyService;
import com.hgurbuz.finartz.flightbookingapi.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompanyResponse addCompany(@Valid @RequestBody CompanyRequest companyRequest){
        return companyService.addCompany(companyRequest);
    }

    @GetMapping
    public List<CompanyResponse> fetchAllCompanies(){
        return companyService.findAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse findCompanyById(@PathVariable Long id){
        return companyService.findCompanyById(id);
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompanyById(@PathVariable Long id,@Valid @RequestBody CompanyRequest companyRequest){
        return companyService.updateCompanyById(id,companyRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id){
        companyService.deleteCompanyById(id);
    }

    @GetMapping("/flights/{id}")
    public List<FlightResponse> getAllFlightsOfCompany(@PathVariable Long id){
        return flightService.findAllFlightsByCompanyId(id);
    }
}
