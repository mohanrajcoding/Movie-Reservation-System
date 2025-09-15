package com.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingConsumer {

    @KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void consume(String message) {
        System.out.println("📩 Received booking event: " + message);
        // Here you could parse JSON and send an email/SMS
    }
}
