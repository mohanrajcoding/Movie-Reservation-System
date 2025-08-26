package com.movie_service.controller;

import com.movie_service.entity.Seat;
import com.movie_service.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{showtimeId}/availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @PathVariable Long showtimeId,
            @RequestParam List<String> seatIds) {

    	Map<String, Boolean> availability = seatService.checkSeatsAvailability(showtimeId, seatIds);
        return ResponseEntity.ok(availability);
//        return ResponseEntity.ok(seats);
    }
    
    @PostMapping("/{showtimeId}/mark-booked")
	public ResponseEntity<Void> markSeatsBooked(
	        @PathVariable Long showtimeId,
	        @RequestBody List<String> seatIds
	) {
    	seatService.markSeatsAsBooked(showtimeId, seatIds);
	    return ResponseEntity.ok().build();
	}
}
