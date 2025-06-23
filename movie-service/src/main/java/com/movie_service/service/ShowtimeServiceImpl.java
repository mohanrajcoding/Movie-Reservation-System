package com.movie_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movie_service.dto.ShowtimeDTO;
import com.movie_service.entity.Movie;
import com.movie_service.entity.Showtime;
import com.movie_service.exception.MovieServiceException;
import com.movie_service.exception.ShowtimeServiceException;
import com.movie_service.repository.MovieRepository;
import com.movie_service.repository.ShowtimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
	
	private final ShowtimeRepository showtimeRepository;
	private final MovieRepository movieRepository;

	@Override
	public Showtime addShowtime(Showtime showtime) {
		// TODO Auto-generated method stub
		boolean exists = showtimeRepository.existsByMovie_IdAndStartTime
				(showtime.getMovie().getId(), showtime.getStartTime());
		if(exists) {
			throw new ShowtimeServiceException("Showtime already exists for movie at given time");
		}
		Movie movie = movieRepository.findById(showtime.getMovie().getId()).orElseThrow(()->new MovieServiceException("Please enter valid movieId") );
		showtime.setMovie(movie);
		Showtime addShowtime = showtimeRepository.save(showtime);
		return addShowtime;
	}

	@Override
	public List<ShowtimeDTO> getShowtimesFromNow() {
		// TODO Auto-generated method stub
		LocalDateTime start= LocalDateTime.now();
		List<Showtime> showtimes = showtimeRepository.findByStartTimeAfter(start);
		List<ShowtimeDTO> showtimeDto = showtimes.stream().map(showtime ->{
			ShowtimeDTO dto = new ShowtimeDTO();
			dto.setId(showtime.getId());
			dto.setAvailableSeats(showtime.getAvailableSeats());
			dto.setMovie(List.of(showtime.getMovie()));
			dto.setStartTime(showtime.getStartTime());
			dto.setTheatre(List.of(showtime.getTheatre()));
			dto.setTotalSeats(showtime.getTotalSeats());
			return dto;
		}).collect(Collectors.toList());
		return showtimeDto;
	}

	@Override
	public List<Showtime> getShowtimesByMovie(Long movieId) {
		// TODO Auto-generated method stub
		return showtimeRepository.findByMovie_Id(movieId);
	}

	@Override
	public List<ShowtimeDTO> getShowtimesByDate(LocalDate date) {
		// TODO Auto-generated method stub
		LocalDateTime start;
		if(date.isEqual(LocalDate.now())) {
			start = LocalDateTime.now();
		}else {
			start = date.atStartOfDay();
		}
		LocalDateTime end = date.plusDays(1).atStartOfDay();
		List<Showtime> showtimes = showtimeRepository.findByStartTimeBetween(start, end);
		if(showtimes.isEmpty()) {
			throw new ShowtimeServiceException("Records not Available");
		}
		List<ShowtimeDTO> showtimeDto = showtimes.stream().map(showtime->{
			ShowtimeDTO dto = new ShowtimeDTO();
			dto.setAvailableSeats(showtime.getAvailableSeats());
			dto.setId(showtime.getId());
			dto.setMovie(List.of(showtime.getMovie()));
			dto.setStartTime(showtime.getStartTime());
			dto.setTheatre(List.of(showtime.getTheatre()));
			dto.setTotalSeats(showtime.getTotalSeats());
			return dto;
		}).collect(Collectors.toList());
		return showtimeDto;
	}

	@Override
	public Showtime updateShowtime(Showtime showtime) {
		// TODO Auto-generated method stub
		Showtime updateShowtime = showtimeRepository.findById(showtime.getId()).orElseThrow(
					()->new ShowtimeServiceException("Showtime already exists for movie at given time"));
		updateShowtime.setAvailableSeats(showtime.getAvailableSeats());
		updateShowtime.setCreatedAt(showtime.getCreatedAt());
		updateShowtime.setMovie(showtime.getMovie());
		updateShowtime.setStartTime(showtime.getStartTime());
		updateShowtime.setTheatre(showtime.getTheatre());
		updateShowtime.setTotalSeats(showtime.getTotalSeats());
		updateShowtime.setUpdatedAt(showtime.getUpdatedAt());
		return updateShowtime;
	}

	@Override
	public void deleteshowtime(Long id) {
		// TODO Auto-generated method stub
		showtimeRepository.findById(id).orElseThrow(()->new ShowtimeServiceException("Show time not available for delete"));
		showtimeRepository.deleteById(id);
		
	}

}
