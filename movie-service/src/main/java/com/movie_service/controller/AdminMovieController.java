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

import com.movie_service.entity.Movie;
import com.movie_service.service.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/movies")
public class AdminMovieController {

	private final MovieService movieSerive;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add-movies")
	public ResponseEntity <Map<String,String>> addMovie(@RequestBody Movie movie){
		Movie addMovie = movieSerive.addMovie(movie);
		return ResponseEntity.ok(Collections.singletonMap("message", addMovie.getTitle()+" movie added Successfully"));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update-movie")
	public ResponseEntity <Movie> updateMovie(@RequestBody Movie movie){
		Movie updatedMovie = movieSerive.updateMovie(movie);
		return ResponseEntity.ok(updatedMovie);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete-movie/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable Long id){
		movieSerive.deleteMovie(id);
		return ResponseEntity.ok(Collections.singletonMap("message", "Movie details deleted successfully"));
	}
}
