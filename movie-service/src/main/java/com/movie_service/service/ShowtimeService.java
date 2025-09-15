package com.movie_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.movie_service.dto.ShowtimeRequestDTO;
import com.movie_service.dto.ShowtimeResponseDTO;
import com.movie_service.entity.Showtime;
import com.movie_service.dto.ShowtimeDetailsDTO;

public interface ShowtimeService {
	void addShowtime(ShowtimeRequestDTO showtime);
	List<Showtime> getShowtimesByMovie(Long movieId);
	List<ShowtimeResponseDTO> getShowtimesByDate(LocalDate date);
	List<ShowtimeResponseDTO> getShowtimesFromNow();
	void deleteshowtime(Long id);
	void updateShowtime(Long id, ShowtimeRequestDTO showtimeRequestDTO);
	Map<String, Boolean> checkSeatsAvailability(Long showtimeId, List<String> seatIds);
	List<ShowtimeResponseDTO> getAllShowtimesByMovieId(Long movieId);
	ShowtimeDetailsDTO getShowtimesById(Long showtimeId);
	
}
