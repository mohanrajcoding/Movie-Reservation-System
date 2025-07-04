package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movie_service.entity.Movie;
import com.movie_service.repository.MovieRepository;
import com.movie_service.repository.ShowtimeRepository;
import com.movie_service.exception.MovieServiceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

	private final MovieRepository movieRepository;
	private final ShowtimeRepository showtimeRepository;
	
	@Override
	public Movie addMovie(Movie movie) {
		// TODO Auto-generated method stub
		if(movie.getTitle().isEmpty() || movie.getTitle()==null) {
			throw new MovieServiceException("Please pass valide Title");
		}
		List<Movie> isExist = movieRepository.findByNormalizedTitle(movie.getTitle().trim().toLowerCase());
		if(!isExist.isEmpty()) {
			throw new MovieServiceException(movie.getTitle()+ " movie already exist");
		}
		String rawTitle = movie.getTitle();
		movie.setNormalizedTitle(rawTitle.trim().toLowerCase());
		return movieRepository.save(movie);
	}

	@Override
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return movieRepository.findAll();
	}

	@Override
	public List<Movie> getMoviesByDate(LocalDate date) {
		// TODO Auto-generated method stub
		List <Movie> movies = showtimeRepository.findAll().stream()
		.filter(showtime->showtime.getStartTime().toLocalDate().isEqual(date))
		.map(showtime -> showtime.getMovie())
		.distinct().collect(Collectors.toList());
		if(movies.isEmpty()) {
			throw new MovieServiceException("Movies not available on: "+ date.toString());
		}
		return movies;
	}

	@Override
	public Movie updateMovie(Movie movie) {
		// TODO Auto-generated method stub
		Movie existingmovie = movieRepository.findById(movie.getId()).orElseThrow(
					()->new MovieServiceException ("Movie is not available for Updating"));
		
		existingmovie.setDescription(movie.getDescription());
		existingmovie.setGenre(movie.getGenre());
		existingmovie.setPosterUrl(movie.getPosterUrl());
		existingmovie.setTitle(movie.getTitle());
		return existingmovie;
	}
	
	@Override
	public void deleteMovie(Integer id) {
		movieRepository.findById(id).orElseThrow(()->new MovieServiceException("There is no Record for id: "+id));
		movieRepository.deleteById(id);
	}
	

}
