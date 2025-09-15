package com.booking_service.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.dto.BookingEvent;
import com.booking_service.kafka.BookingProducer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings/test")
@RequiredArgsConstructor
public class TestBookingController {

	private final BookingProducer bookingProducer;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestParam String movie, @RequestParam String email) {
    	
    	System.out.println("Inside Test Controller");
        BookingEvent event = new BookingEvent(
                UUID.randomUUID().toString(),
                movie,
                email
        );

        bookingProducer.sendDummyBookingEvent(event);

        return ResponseEntity.ok("Booking created & event published!");
    }
}
