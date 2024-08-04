package com.ynmio.School.Management.repository;

import com.ynmio.School.Management.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTicketId(String ticketId);
    List<Ticket> findByState(String state);
}

