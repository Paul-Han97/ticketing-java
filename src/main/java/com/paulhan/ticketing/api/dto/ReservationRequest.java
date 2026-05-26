package com.paulhan.ticketing.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {

    @NotBlank
    private String eventId;

    @Min(1)
    private int quantity;

    public ReservationRequest() {
    }

    public ReservationRequest(String eventId, int quantity) {
        this.eventId = eventId;
        this.quantity = quantity;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
