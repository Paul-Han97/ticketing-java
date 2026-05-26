package com.paulhan.ticketing.exception;

public class ReservationNotFoundException extends ReservationException {

    public ReservationNotFoundException(String eventId) {
        super("Ticket inventory not found for event: " + eventId);
    }
}
