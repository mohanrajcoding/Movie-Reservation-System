package com.notification_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification_service.dto.BookingEvent;
import com.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void consume(String message) {
        try {
            BookingEvent event = objectMapper.readValue(message, BookingEvent.class);
            System.out.println("📩 Received booking event: " + message);
            // Send email
            emailService.sendBookingConfirmation(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
