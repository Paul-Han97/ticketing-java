package com.paulhan.ticketing.exception;

public class InvalidReservationRequestException extends ReservationException {

    public InvalidReservationRequestException(String message) {
        super(message);
    }
}
