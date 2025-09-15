package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.movie_service.entity.Movie;
import com.movie_service.repository.MovieRepository;
import com.movie_service.repository.ShowtimeRepository;
import com.movie_service.exception.MovieServiceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public Movie addMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new MovieServiceException("Please pass valid Title");
        }

        List<Movie> isExist = movieRepository.findByNormalizedTitle(movie.getTitle().trim().toLowerCase());
        if (!isExist.isEmpty()) {
            throw new MovieServiceException(movie.getTitle() + " movie already exists");
        }

        movie.setNormalizedTitle(movie.getTitle().trim().toLowerCase());
        return movieRepository.save(movie);
    }

    @Override
    @Cacheable(value = "movies", key = "'all'")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Cacheable(value = "movies", key = "#date")
    public List<Movie> getMoviesByDate(LocalDate date) {
        List<Movie> movies = showtimeRepository.findAll().stream()
                .filter(showtime -> showtime.getStartTime().toLocalDate().isEqual(date))
                .map(showtime -> showtime.getMovie())
                .distinct()
                .collect(Collectors.toList());

        if (movies.isEmpty()) {
            throw new MovieServiceException("Movies not available on: " + date);
        }

        return movies;
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public Movie updateMovie(Movie movie) {
        Movie existingMovie = movieRepository.findById(movie.getId())
                .orElseThrow(() -> new MovieServiceException("Movie not available for updating"));

        existingMovie.setDescription(movie.getDescription());
        existingMovie.setGenre(movie.getGenre());
        existingMovie.setPosterUrl(movie.getPosterUrl());
        existingMovie.setTitle(movie.getTitle());

        return movieRepository.save(existingMovie); // 🔑 FIXED
    }

    @Override
    @CacheEvict(value = "movies", allEntries = true)
    public void deleteMovie(Long id) {
        movieRepository.findById(id)
                .orElseThrow(() -> new MovieServiceException("No record found for id: " + id));
        movieRepository.deleteById(id);
    }
}
