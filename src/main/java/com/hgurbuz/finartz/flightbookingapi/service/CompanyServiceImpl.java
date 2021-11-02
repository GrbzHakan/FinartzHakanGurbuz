package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.CompanyConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse addCompany(CompanyRequest companyRequest) {
        var company = CompanyConverter.convert(companyRequest);
        return CompanyResponse.of(companyRepository.save(company));

    }

    @Override
    public List<CompanyResponse> findAllCompanies() {
        return CompanyResponse.of(companyRepository.findAll());
    }

    @Override
    public CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest) {
        CompanyResponse companyResponse = findCompanyById(id);
        companyResponse.setName(companyRequest.getName());
        return CompanyResponse.of(companyRepository.save(CompanyConverter.convert(companyResponse)));
    }

    @Override
    public CompanyResponse findCompanyById(Long id) {
        var company = companyRepository.findById(id);
        if(company.isPresent()){
            return CompanyResponse.of(company.get());
        }else{
            throw new NotFoundException(ExceptionConstant.COMPANY_NOT_FOUND_MESSAGE + id);
        }
    }



    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }
}
