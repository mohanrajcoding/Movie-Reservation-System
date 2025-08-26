package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;

import com.movie_service.entity.Movie;

public interface MovieService {
	Movie addMovie(Movie movie);
	List<Movie> getAllMovies();
	List<Movie> getMoviesByDate(LocalDate date);
	Movie updateMovie(Movie movie);
	void deleteMovie(Long id);
}
