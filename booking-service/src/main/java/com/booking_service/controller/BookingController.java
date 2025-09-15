package com.booking_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.dto.BookingDetailsDTO;
import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;
import com.booking_service.kafka.BookingProducer;
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
            @RequestHeader("X-User-Email") String email,
            @Valid @RequestBody ConfirmBookingRequestDTO req) {
    	
        return ResponseEntity.ok(bookingService.confirm(userId, email, req));
    }
    
    @GetMapping("/myBooking")
    public ResponseEntity<List<BookingResponseDTO>> myBookings(@RequestHeader("X-User-Id") String userId){
    	List<BookingResponseDTO> res = bookingService.myBookings(userId);
    	return ResponseEntity.ok(res);
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingDetailsbyId(@PathVariable String bookingId){
		return ResponseEntity.ok(bookingService.getBookingDetailsbyId(bookingId));
    }

}
