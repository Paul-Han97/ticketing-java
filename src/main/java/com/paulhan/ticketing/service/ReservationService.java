package com.paulhan.ticketing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulhan.ticketing.api.dto.InventoryResponse;
import com.paulhan.ticketing.api.dto.ReservationRequest;
import com.paulhan.ticketing.api.dto.ReservationResponse;
import com.paulhan.ticketing.model.Ticket;
import com.paulhan.ticketing.repository.TicketRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.paulhan.ticketing.exception.IdempotencyConflictException;
import com.paulhan.ticketing.exception.InvalidReservationRequestException;
import com.paulhan.ticketing.exception.ReservationFailedException;
import com.paulhan.ticketing.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    private static final Duration IDEMPOTENCY_TTL = Duration.ofHours(24);
    private static final Duration HOLD_TTL = Duration.ofMinutes(5);

    private final TicketRepository ticketRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public ReservationService(
            TicketRepository ticketRepository,
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper) {
        this.ticketRepository = ticketRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public InventoryResponse createOrUpdateInventory(String eventId, int available) {
        if (available < 0) {
            throw new InvalidReservationRequestException("Available quantity must be zero or positive");
        }

        Ticket ticket = ticketRepository.findByEventId(eventId).orElseGet(() -> new Ticket(eventId, available));
        ticket.setAvailable(available);
        ticketRepository.save(ticket);
        return new InventoryResponse(ticket.getEventId(), ticket.getAvailable());
    }

    public InventoryResponse findInventory(String eventId) {
        Ticket ticket = ticketRepository.findByEventId(eventId)
                .orElseThrow(() -> new ReservationNotFoundException(eventId));
        return new InventoryResponse(ticket.getEventId(), ticket.getAvailable());
    }

    public ReservationResponse reserve(ReservationRequest request, String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new InvalidReservationRequestException("X-Idempotency-Key header is required");
        }
        if (request.getQuantity() <= 0) {
            throw new InvalidReservationRequestException("Requested quantity must be positive");
        }

        String requestPayload = buildRequestPayload(request);
        String requestKey = idempotencyRequestKey(idempotencyKey);
        String responseKey = idempotencyResponseKey(idempotencyKey);

        String existingRequest = redisTemplate.opsForValue().get(requestKey);
        if (existingRequest != null) {
            if (!existingRequest.equals(requestPayload)) {
                throw new IdempotencyConflictException("Idempotency key already used with different request payload");
            }
            return loadResponse(responseKey);
        }

        Ticket ticket = ticketRepository.findByEventId(request.getEventId())
                .orElseThrow(() -> new ReservationNotFoundException(request.getEventId()));

        if (ticket.getAvailable() < request.getQuantity()) {
            throw new ReservationFailedException("Not enough inventory for event: " + request.getEventId());
        }

        storeReservationHold(idempotencyKey, request);
        ReservationResponse response = new ReservationResponse(
                "ACCEPTED",
                request.getEventId(),
                request.getQuantity(),
                "Reservation hold created",
                idempotencyKey);

        saveIdempotencyRecord(requestKey, responseKey, requestPayload, response);
        return response;
    }

    private String buildRequestPayload(ReservationRequest request) {
        return request.getEventId() + ":" + request.getQuantity();
    }

    private void saveIdempotencyRecord(String requestKey, String responseKey, String requestPayload, ReservationResponse response) {
        boolean success = redisTemplate.opsForValue().setIfAbsent(requestKey, requestPayload, IDEMPOTENCY_TTL);
        if (!success) {
            throw new IdempotencyConflictException("Idempotency key is already recorded");
        }
        try {
            String serialized = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(responseKey, serialized, IDEMPOTENCY_TTL);
        } catch (JsonProcessingException ex) {
            throw new ReservationFailedException("Unable to serialize reservation response");
        }
    }

    private ReservationResponse loadResponse(String responseKey) {
        String json = redisTemplate.opsForValue().get(responseKey);
        if (json == null) {
            throw new ReservationFailedException("Idempotency response record is missing");
        }
        try {
            return objectMapper.readValue(json, ReservationResponse.class);
        } catch (JsonProcessingException ex) {
            throw new ReservationFailedException("Unable to deserialize idempotency response");
        }
    }

    private void storeReservationHold(String idempotencyKey, ReservationRequest request) {
        String holdKey = reservationHoldKey(idempotencyKey);
        String payload = request.getEventId() + ":" + request.getQuantity();
        boolean stored = redisTemplate.opsForValue().setIfAbsent(holdKey, payload, HOLD_TTL);
        if (!stored) {
            throw new ReservationFailedException("Reservation hold already exists for idempotency key");
        }
    }

    private String idempotencyRequestKey(String token) {
        return "idempotency:request:" + token;
    }

    private String idempotencyResponseKey(String token) {
        return "idempotency:response:" + token;
    }

    private String reservationHoldKey(String token) {
        return "reservation:hold:" + token;
    }
}
