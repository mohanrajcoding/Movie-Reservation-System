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

import com.movie_service.dto.ShowtimeRequestDTO;
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
	public ResponseEntity<Map<String, String>> addShowtime(@RequestBody ShowtimeRequestDTO showtimeDto){
		showtimeService.addShowtime(showtimeDto);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime added Successfully"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update-showtime")
	public ResponseEntity<Map<String,String>> updateShowtime(@RequestBody ShowtimeRequestDTO showtime){
		showtimeService.updateShowtime(showtime);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime updated Successfully"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete-showtime/{id}")
	public ResponseEntity<?> deleteShowtime(@PathVariable Integer id){
		showtimeService.deleteshowtime(id);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime details deleted successfully"));
	}
}
