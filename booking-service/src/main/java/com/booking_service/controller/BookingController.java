package com.booking_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;
import com.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;
    @PostMapping("/confirm")
    public ResponseEntity<BookingResponseDTO> confirm(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody ConfirmBookingRequestDTO req) {
        return ResponseEntity.ok(bookingService.confirm(userId, req));
    }
    
    @GetMapping
    public String home() {
    	return "Hi home";
    }
}
