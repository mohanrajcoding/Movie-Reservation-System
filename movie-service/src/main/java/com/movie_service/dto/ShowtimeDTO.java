package com.movie_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.movie_service.entity.Movie;
import com.movie_service.entity.Theatre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeDTO {
	
	private Long Id;
	private List<Movie> movie;
	private List<Theatre> theatre;
	private LocalDateTime startTime;
	private Integer totalSeats;
	private Integer availableSeats;

}
