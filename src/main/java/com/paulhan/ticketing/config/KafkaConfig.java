package com.paulhan.ticketing.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic reservationRequestsTopic() {
        return new NewTopic("reservation-requests", 3, (short)1);
    }

    @Bean
    public NewTopic reservationDlqTopic() {
        return new NewTopic("reservation-dlq", 1, (short)1);
    }
}
