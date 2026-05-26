package com.paulhan.ticketing.exception;

public class IdempotencyConflictException extends ReservationException {

    public IdempotencyConflictException(String message) {
        super(message);
    }
}
