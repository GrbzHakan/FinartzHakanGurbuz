package com.hgurbuz.finartz.flightbookingapi.model.response;

import com.hgurbuz.finartz.flightbookingapi.converter.AirportConverter;
import com.hgurbuz.finartz.flightbookingapi.converter.CompanyConverter;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompanyResponse {
    private Long id;
    private String name;


    public static List<CompanyResponse> of(List<Company> companyList) {
        return companyList.stream()
                .map(CompanyConverter::convert).collect(Collectors.toList());
    }
    public static CompanyResponse of(Company company) {
        return CompanyConverter.convert(company);
    }
}
