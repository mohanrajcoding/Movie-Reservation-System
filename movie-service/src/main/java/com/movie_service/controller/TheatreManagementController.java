package com.movie_service.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.entity.Theatre;
import com.movie_service.service.TheatreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/theater")
@RequiredArgsConstructor
public class TheatreManagementController {
	
	private final TheatreService theatreService;

	@PostMapping("/addTheatre")
	public ResponseEntity<?> addTheatre(@RequestBody Theatre theatre) {
		theatreService.addTheatre(theatre);
		return ResponseEntity.ok(Collections.singletonMap("message", "Theatre was added Successfully"));
	}
	
	@PutMapping("/updateTheatre")
	public ResponseEntity<?> modifyTheatre(@RequestBody Theatre theatre){
		theatreService.updateTheatreById(theatre);
		return ResponseEntity.ok(Collections.singletonMap("message", "Theatre was updated successfully"));
	}
	
	@GetMapping
	public ResponseEntity<?> getTheatre(@RequestParam (required = false) Integer id){
		if(id==null) {
			return ResponseEntity.ok(theatreService.getTheatres());
		}
		return ResponseEntity.ok(theatreService.getTheatreById(id));
	}
}
