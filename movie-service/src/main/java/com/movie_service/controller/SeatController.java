package com.movie_service.controller;

import com.movie_service.entity.Seat;
import com.movie_service.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{id}/seats/availability")
    public ResponseEntity<List<Seat>> checkAvailability(
            @PathVariable("id") Long showtimeId,
            @RequestParam List<String> seatIds) {

        List<Seat> seats = seatService.checkSeatAvailability(showtimeId, seatIds);
        return ResponseEntity.ok(seats);
    }
}
