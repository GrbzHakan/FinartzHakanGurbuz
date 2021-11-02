package com.hgurbuz.finartz.flightbookingapi.repository;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
}
