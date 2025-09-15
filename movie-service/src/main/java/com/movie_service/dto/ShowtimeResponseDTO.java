package com.movie_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
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
public class ShowtimeResponseDTO {
	
	private Long id;
	//private List<Movie> movie;
	//private List<Theatre> theatre;
	private String movieName;
	private String theatreName;
	private String location;
	private LocalDateTime startTime;
	private Integer totalSeats;
	private Integer availableSeats;
	private String screenName;
	private BigDecimal price;

}
