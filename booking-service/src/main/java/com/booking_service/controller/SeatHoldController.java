package com.booking_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.dto.CreateHoldRequestDTO;
import com.booking_service.dto.CreateHoldResponseDTO;
import com.booking_service.service.SeatHoldService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class SeatHoldController {
	private final SeatHoldService seatHoldService;
    @PostMapping
    public ResponseEntity<CreateHoldResponseDTO> createHold(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateHoldRequestDTO req) {
    	if (req.getShowtimeId() == null) {
    	    throw new IllegalArgumentException("showtimeId cannot be null");
    	}
        return ResponseEntity.ok(seatHoldService.createHold(userId, req));
    }

    @DeleteMapping("/{holdId}")
    public ResponseEntity<Void> cancelHold(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String holdId) {
        seatHoldService.cancelHold(userId, holdId);
        return ResponseEntity.noContent().build();
    }

}