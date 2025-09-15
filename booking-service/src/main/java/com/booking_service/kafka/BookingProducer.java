package com.booking_service.kafka;

import com.booking_service.dto.BookingEvent;
import com.booking_service.dto.BookingResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendBookingEvent(BookingResponseDTO event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("booking-events", json);
            System.out.println("📤 Sent booking event: " + json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing booking event", e);
        }
    }
    
    public void sendDummyBookingEvent(BookingEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("booking-events", json);
            System.out.println("📤 Sent booking event: " + json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing booking event", e);
        }
    }
}