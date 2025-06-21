package com.movie_service.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.entity.Movie;
import com.movie_service.service.MovieSerive;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class UserMovieController {

	private final MovieSerive movieSerive;
	
	@GetMapping
	public ResponseEntity <List<Movie>> getMovies(@RequestParam (required = false) 
											@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
		
		if(date!=null) {
			return ResponseEntity.ok(movieSerive.getMoviesByDate(date));
		}
		return ResponseEntity.ok(movieSerive.getAllMovies());
		
	}
}
