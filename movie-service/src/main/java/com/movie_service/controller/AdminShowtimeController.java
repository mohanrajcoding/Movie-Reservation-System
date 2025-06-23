package com.movie_service.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.entity.Showtime;
import com.movie_service.service.ShowtimeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/showtime")
public class AdminShowtimeController {
	
	private final ShowtimeService showtimeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add-showtime")
	public ResponseEntity <Showtime> addShowtime(@RequestBody Showtime showtime){
		Showtime addShowtime = showtimeService.addShowtime(showtime);
		return ResponseEntity.ok(addShowtime);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update-showtime")
	public ResponseEntity<Showtime> updateShowtime(@RequestBody Showtime showtime){
		Showtime updateShowtime = showtimeService.updateShowtime(showtime);
		return ResponseEntity.ok(updateShowtime);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete-showtime/{id}")
	public ResponseEntity<?> deleteShowtime(@PathVariable Long id){
		showtimeService.deleteshowtime(id);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime details deleted successfully"));
	}
}
