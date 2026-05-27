package com.paulhan.ticketing.api.dto;

public class ReservationMessage {
    private String idempotencyKey;
    private String eventId;
    private int quantity;
    private int attempts = 0;

    public ReservationMessage() {}

    public ReservationMessage(String idempotencyKey, String eventId, int quantity) {
        this.idempotencyKey = idempotencyKey;
        this.eventId = eventId;
        this.quantity = quantity;
        this.attempts = 0;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
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

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
