package com.movie_service.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.dto.ShowtimeResponseDTO;
import com.movie_service.service.ShowtimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies/showtimes")
public class ShowtimeController {
	
	private final ShowtimeService showtimeService;
	
	@GetMapping
	public ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimes(@RequestParam (required = false) 
											@DateTimeFormat (iso= DateTimeFormat.ISO.DATE) LocalDate date){
		if(date!=null) {
			return ResponseEntity.ok(showtimeService.getShowtimesByDate(date));
		}
		return ResponseEntity.ok(showtimeService.getShowtimesFromNow());
	}
	
	@GetMapping("/{date}")
	public ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimesBydate(@PathVariable 
											@DateTimeFormat (iso= DateTimeFormat.ISO.DATE) LocalDate date){
		if(date!=null) {
			return ResponseEntity.ok(showtimeService.getShowtimesByDate(date));
		}
		return ResponseEntity.ok(showtimeService.getShowtimesFromNow());
	}
	
	/*** @GetMapping("/{id}/seats/availability")
	public ResponseEntity<Map<String, Boolean>> checkSeatAvailability(
	            @PathVariable Long id,
	            @RequestParam List<String> seatIds) {

	        Map<String, Boolean> availability = showtimeService.checkSeatsAvailability(id, seatIds);
	        return ResponseEntity.ok(availability);
	    }
	
	@PostMapping("/{showtimeId}/seats/mark-booked")
	public ResponseEntity<Void> markSeatsBooked(
	        @PathVariable Long showtimeId,
	        @RequestBody List<String> seatIds
	) {
		showtimeService.markSeatsAsBooked(showtimeId, seatIds);
	    return ResponseEntity.ok().build();
	} ***/
	

}
