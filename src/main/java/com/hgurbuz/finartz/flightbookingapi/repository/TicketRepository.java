package com.hgurbuz.finartz.flightbookingapi.repository;

import com.hgurbuz.finartz.flightbookingapi.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
