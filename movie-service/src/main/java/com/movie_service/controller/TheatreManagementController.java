package com.movie_service.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
