package com.paulhan.ticketing.api.dto;

public class InventoryResponse {

    private String eventId;
    private Integer available;

    public InventoryResponse() {
    }

    public InventoryResponse(String eventId, Integer available) {
        this.eventId = eventId;
        this.available = available;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}
