package com.paulhan.ticketing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulhan.ticketing.api.dto.ReservationRequest;
import com.paulhan.ticketing.api.dto.ReservationResponse;
import com.paulhan.ticketing.model.Ticket;
import com.paulhan.ticketing.repository.TicketRepository;
import java.util.Optional;
import com.paulhan.ticketing.exception.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        reservationService = new ReservationService(ticketRepository, redisTemplate, new ObjectMapper());
    }

    @Test
    void reserve_shouldReturnAccepted_forNewIdempotentReservation() {
        ReservationRequest request = new ReservationRequest("concert-1", 2);
        when(ticketRepository.findByEventId("concert-1"))
                .thenReturn(Optional.of(new Ticket("concert-1", 10)));
        when(valueOperations.get("idempotency:request:token-123")).thenReturn(null);
        when(valueOperations.setIfAbsent(eq("idempotency:request:token-123"), eq("concert-1:2"), any()))
                .thenReturn(Boolean.TRUE);
        when(valueOperations.setIfAbsent(eq("reservation:hold:token-123"), eq("concert-1:2"), any()))
                .thenReturn(Boolean.TRUE);

        ReservationResponse response = reservationService.reserve(request, "token-123");

        assertEquals("ACCEPTED", response.getStatus());
        assertEquals("concert-1", response.getEventId());
        assertEquals(2, response.getQuantity());
        assertEquals("token-123", response.getIdempotencyKey());
    }

    @Test
    void reserve_shouldThrow_whenInventoryMissing() {
        ReservationRequest request = new ReservationRequest("concert-2", 1);
        when(ticketRepository.findByEventId("concert-2")).thenReturn(Optional.empty());
        when(valueOperations.get("idempotency:request:token-456")).thenReturn(null);

        assertThrows(ReservationNotFoundException.class,
                () -> reservationService.reserve(request, "token-456"));
    }
}
