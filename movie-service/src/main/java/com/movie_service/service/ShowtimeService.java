package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;

import com.movie_service.dto.ShowtimeRequestDTO;
import com.movie_service.dto.ShowtimeResponseDTO;
import com.movie_service.entity.Showtime;

public interface ShowtimeService {
	void addShowtime(ShowtimeRequestDTO showtime);
	List<Showtime> getShowtimesByMovie(Integer movieId);
	List<ShowtimeResponseDTO> getShowtimesByDate(LocalDate date);
	List<ShowtimeResponseDTO> getShowtimesFromNow();
	void updateShowtime(ShowtimeRequestDTO showtime);
	void deleteshowtime(Integer id);
	
}
