package com.paulhan.ticketing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulhan.ticketing.api.dto.ReservationMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ReservationProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendReservation(ReservationMessage msg) {
        try {
            String payload = objectMapper.writeValueAsString(msg);
            kafkaTemplate.send("reservation-requests", msg.getIdempotencyKey(), payload);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize reservation message", ex);
        }
    }

    public void sendToDlq(ReservationMessage msg, String reason) {
        try {
            // attach simple reason field by serializing a small wrapper
            String payload = objectMapper.writeValueAsString(msg);
            kafkaTemplate.send("reservation-dlq", msg.getIdempotencyKey(), payload);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to serialize dlq message", ex);
        }
    }
}
