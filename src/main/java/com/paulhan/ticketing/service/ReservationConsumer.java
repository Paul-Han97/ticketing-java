package com.paulhan.ticketing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulhan.ticketing.api.dto.ReservationMessage;
import com.paulhan.ticketing.exception.ReservationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ReservationConsumer.class);

    private final ObjectMapper objectMapper;
    private final ReservationService reservationService;
    private final ReservationProducer producer;

    public ReservationConsumer(ObjectMapper objectMapper, ReservationService reservationService, ReservationProducer producer) {
        this.objectMapper = objectMapper;
        this.reservationService = reservationService;
        this.producer = producer;
    }

    @KafkaListener(topics = "reservation-requests", groupId = "reservation-workers")
    public void consume(String payload) {
        try {
            ReservationMessage msg = objectMapper.readValue(payload, ReservationMessage.class);
            try {
                reservationService.finalizeReservation(msg.getIdempotencyKey());
                logger.info("Finalized reservation {}", msg.getIdempotencyKey());
            } catch (ReservationFailedException ex) {
                // retry logic: allow up to 3 attempts
                int attempts = msg.getAttempts();
                if (attempts < 2) {
                    msg.setAttempts(attempts + 1);
                    logger.warn("Transient failure finalizing {}, retrying attempt {}: {}", msg.getIdempotencyKey(), msg.getAttempts(), ex.getMessage());
                    producer.sendReservation(msg);
                } else {
                    logger.error("Finalizing failed after retries {}, sending to DLQ: {}", msg.getIdempotencyKey(), ex.getMessage());
                    producer.sendToDlq(msg, ex.getMessage());
                }
            }
        } catch (Exception ex) {
            logger.error("Failed to process reservation message", ex);
        }
    }
}
