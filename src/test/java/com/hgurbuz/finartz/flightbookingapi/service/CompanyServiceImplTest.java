package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.request.AirportRequest;
import com.hgurbuz.finartz.flightbookingapi.model.request.CompanyRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.AirportResponse;
import com.hgurbuz.finartz.flightbookingapi.model.response.CompanyResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyServiceImpl companyService;

    @Test
    public void whenAddCompanyValidRequest_itShouldSaveWithValidResponse(){
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setName("Turkish Airlines");

        Company company = new Company();
        company.setName("Turkish Airlines");
        company.setFlights(new ArrayList<>());

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName("Turkish Airlines");


        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(company);

        CompanyResponse result = companyService.addCompany(companyRequest);
        assertEquals(result,companyResponse);

        Mockito.verify(companyRepository).save(company);

    }

    @Test
    public void whenFetchAllCompanies_itShouldReturnValidList(){
        Company company1 = new Company();
        company1.setFlights(new ArrayList<>());
        company1.setName("Onur Air");
        company1.setId(1L);

        Company company2 = new Company();
        company2.setFlights(new ArrayList<>());
        company2.setName("Turkish Airlines");
        company2.setId(2L);

        Company company3 = new Company();
        company3.setFlights(new ArrayList<>());
        company3.setName("Pegasus Air");
        company3.setId(3L);

        CompanyResponse companyResponse1 = new CompanyResponse();
        companyResponse1.setId(1L);
        companyResponse1.setName("Onur Air");

        CompanyResponse companyResponse2 = new CompanyResponse();
        companyResponse2.setId(2L);
        companyResponse2.setName("Turkish Airlines");

        CompanyResponse companyResponse3 = new CompanyResponse();
        companyResponse3.setId(3L);
        companyResponse3.setName("Pegasus Air");

        List<Company> companyList = List.of(company1,company2,company3);

        Mockito.when(companyRepository.findAll()).thenReturn(companyList);

        List<CompanyResponse> companyResponseList = CompanyResponse.of(companyList);

        List<CompanyResponse> result = companyService.findAllCompanies();

        assertEquals(result,companyResponseList);

        Mockito.verify(companyRepository).findAll();

    }

    @Test
    public void whenFindCompanyWithValidId_itShouldReturnValidResponse(){
        Company company = new Company();
        company.setId(1L);
        company.setName("Turkish Airlines");

        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName("Turkish Airlines");
        companyResponse.setId(1L);
        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(company));
        CompanyResponse result = companyService.findCompanyById(company.getId());

        assertEquals(result,companyResponse);

        Mockito.verify(companyRepository).findById(company.getId());

    }



    @Test
    public void itShouldThrowNotFoundException_WhenThereAreNoAnyCompany() {
        Company company = new Company();
        company.setName("Turkish Airlines");
        company.setId(1L);

        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
                        companyService.findCompanyById(company.getId()),
                NotFoundException.class);
        Assertions.assertThat(notFoundException).isNotNull();
        Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Company not found with the given id " + company.getId());

        Mockito.verify(companyRepository).findById(company.getId());

    }

    @Test
    public void whenUpdateAirportWithValidIdAndRequest_itShouldReturnValidResponse(){
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setName("Pegasus");


        Company optionalCompany = new Company();
        optionalCompany.setId(1L);
        optionalCompany.setName("Turkish Airlines");
        optionalCompany.setFlights(new ArrayList<>());

        CompanyResponse companyExpectedResponse = new CompanyResponse();
        companyExpectedResponse.setName("Pegasus");
        companyExpectedResponse.setId(1L);

        Company companyConverted = new Company();
        companyConverted.setFlights(new ArrayList<>());
        companyConverted.setName("Pegasus");
        companyConverted.setId(1L);


        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(optionalCompany));;
        Mockito.when(companyRepository.save(Mockito.any(Company.class))).thenReturn(companyConverted);

        CompanyResponse result = companyService.updateCompanyById(1L,companyRequest);

        assertEquals(companyExpectedResponse,result);

        Mockito.verify(companyRepository).findById(Mockito.anyLong());
        Mockito.verify(companyRepository).save(Mockito.any(Company.class));


    }

    @Test
    public void givenIdToDeleteThen_itShouldDeleteThCompany(){
        companyService.deleteCompanyById(1000L);
        Mockito.verify(companyRepository).deleteById(1000L);

    }
}