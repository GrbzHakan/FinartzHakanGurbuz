package com.hgurbuz.finartz.flightbookingapi.repository;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findFlightsByCompany_Id(Long companyId);
    List<Flight> findFlightsByRoute_Id(Long routeId);
    @Modifying
    @Transactional
    @Query("update Flight f set f.remainCapacity = :remainCapacity where f.id = :id")
    int updateRemainCapacity( @Param("remainCapacity") int remainCapacity, @Param("id") Long id);
 }
