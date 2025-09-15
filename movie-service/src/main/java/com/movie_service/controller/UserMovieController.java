package com.movie_service.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie_service.entity.Movie;
import com.movie_service.entity.Theatre;
import com.movie_service.service.MovieService;
import com.movie_service.service.TheatreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class UserMovieController {

	private final MovieService movieSerive;
	private final TheatreService theatreService;
	
	@GetMapping
	public ResponseEntity <List<Movie>> getMovies(@RequestParam (required = false) 
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
		
		if(date!=null) {
		return ResponseEntity.ok(movieSerive.getMoviesByDate(date));
		}
		return ResponseEntity.ok(movieSerive.getAllMovies());
		
	}
	
	@GetMapping("/theatre/{id}")
	public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id){
		return ResponseEntity.ok(theatreService.getTheatreById(id));
	}
	
	@GetMapping("/theatres")
	public ResponseEntity <List<Theatre>> getTheatres(){
		return ResponseEntity.ok(theatreService.getTheatres());
	}
}
