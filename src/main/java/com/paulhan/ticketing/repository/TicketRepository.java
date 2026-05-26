package com.paulhan.ticketing.repository;

import com.paulhan.ticketing.model.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByEventId(String eventId);

    @Modifying
    @Transactional
    @Query("UPDATE Ticket t SET t.available = t.available - :qty WHERE t.eventId = :eventId AND t.available >= :qty")
    int decrementAvailableIfEnough(@Param("eventId") String eventId, @Param("qty") int qty);
}
