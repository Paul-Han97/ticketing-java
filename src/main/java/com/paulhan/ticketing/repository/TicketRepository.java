package com.paulhan.ticketing.repository;

import com.paulhan.ticketing.model.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByEventId(String eventId);
}
