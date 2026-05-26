package com.paulhan.ticketing.api.dto;

public class ReservationResponse {

    private String status;
    private String eventId;
    private int quantity;
    private String message;
    private String idempotencyKey;

    public ReservationResponse() {
    }

    public ReservationResponse(String status, String eventId, int quantity, String message, String idempotencyKey) {
        this.status = status;
        this.eventId = eventId;
        this.quantity = quantity;
        this.message = message;
        this.idempotencyKey = idempotencyKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
