package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;

import java.util.List;


public interface CompanyService {
    CompanyResponse addCompany(CompanyRequest companyRequest);
    List<CompanyResponse> findAllCompanies();
    CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest);
    CompanyResponse findCompanyById(Long id);
    void deleteCompanyById(Long id);
}
