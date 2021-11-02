package com.hgurbuz.finartz.flightbookingapi.converter;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyConverter {
    public static CompanyResponse convert(Company company) {
        var companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        return companyResponse;
    }

    public static Company convert(CompanyRequest companyRequest) {
        var company = new Company();
        company.setName(companyRequest.getName());
        company.setFlights(new ArrayList<>());
        return company;
    }


    public static Company convert(CompanyResponse companyResponse) {
        var company = new Company();
        company.setId(companyResponse.getId());
        company.setName(companyResponse.getName());
        return company;
    }
}
