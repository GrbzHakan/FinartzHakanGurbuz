package com.hgurbuz.finartz.flightbookingapi.repository;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository  extends JpaRepository<Airport,Long> {
    boolean existsAirportsByCity(String city);
}
