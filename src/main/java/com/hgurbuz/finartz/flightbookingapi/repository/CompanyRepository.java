package com.hgurbuz.finartz.flightbookingapi.repository;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Company;
import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

}
