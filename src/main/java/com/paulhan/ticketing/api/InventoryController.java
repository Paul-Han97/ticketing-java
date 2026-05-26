package com.paulhan.ticketing.api;

import com.paulhan.ticketing.api.dto.InventoryResponse;
import com.paulhan.ticketing.api.dto.InventoryUpdateRequest;
import com.paulhan.ticketing.api.dto.ReservationRequest;
import com.paulhan.ticketing.api.dto.ReservationResponse;
import com.paulhan.ticketing.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final ReservationService reservationService;

    public InventoryController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{eventId}")
    public InventoryResponse getInventory(@PathVariable String eventId) {
        return reservationService.findInventory(eventId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@Valid @RequestBody InventoryUpdateRequest request) {
        return reservationService.createOrUpdateInventory(request.getEventId(), request.getAvailable());
    }

    @PostMapping("/reserve")
    public ReservationResponse reserve(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ReservationRequest request) {
        return reservationService.reserve(request, idempotencyKey);
    }
}
