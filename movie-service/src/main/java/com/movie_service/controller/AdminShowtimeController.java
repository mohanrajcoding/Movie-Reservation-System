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
@RequestMapping("/api/movies/admin/showtime")
public class AdminShowtimeController {
	
	private final ShowtimeService showtimeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addshowtime")
	public ResponseEntity<Map<String, String>> addShowtime(@RequestBody ShowtimeRequestDTO showtimeDto){
		showtimeService.addShowtime(showtimeDto);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime added Successfully"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Map<String,String>> updateShowtime(@PathVariable Long id, 
													@RequestBody ShowtimeRequestDTO showtimeRequestDTO){
		showtimeService.updateShowtime(id,showtimeRequestDTO);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime updated Successfully"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteShowtime(@PathVariable Long id){
		showtimeService.deleteshowtime(id);
		return ResponseEntity.ok(Collections.singletonMap("message", "Showtime details deleted successfully"));
	}
}
