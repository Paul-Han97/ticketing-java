package com.paulhan.ticketing.api;

import com.paulhan.ticketing.exception.IdempotencyConflictException;
import com.paulhan.ticketing.exception.InvalidReservationRequestException;
import com.paulhan.ticketing.exception.ReservationFailedException;
import com.paulhan.ticketing.exception.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(InvalidReservationRequestException.class)
    public ResponseEntity<String> handleBadRequest(InvalidReservationRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IdempotencyConflictException.class)
    public ResponseEntity<String> handleConflict(IdempotencyConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ReservationFailedException.class)
    public ResponseEntity<String> handleFailed(ReservationFailedException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}
