package com.railiac.rest.database.repository;

import com.railiac.rest.database.model.Ticket;
import com.railiac.rest.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}