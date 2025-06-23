package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;

import com.movie_service.dto.ShowtimeDTO;
import com.movie_service.entity.Showtime;

public interface ShowtimeService {
	Showtime addShowtime(Showtime showtime);
	List<Showtime> getShowtimesByMovie(Long movieId);
	List<ShowtimeDTO> getShowtimesByDate(LocalDate date);
	List<ShowtimeDTO> getShowtimesFromNow();
	Showtime updateShowtime(Showtime showtime);
	void deleteshowtime(Long id);
	
}
