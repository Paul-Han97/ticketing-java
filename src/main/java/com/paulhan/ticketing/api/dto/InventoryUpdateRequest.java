package com.paulhan.ticketing.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class InventoryUpdateRequest {

    @NotBlank
    private String eventId;

    @Min(0)
    private int available;

    public InventoryUpdateRequest() {
    }

    public InventoryUpdateRequest(String eventId, int available) {
        this.eventId = eventId;
        this.available = available;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
