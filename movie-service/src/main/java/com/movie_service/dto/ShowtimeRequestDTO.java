package com.movie_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeRequestDTO {
	
	private Integer id;
	private LocalDateTime startTime;
	private Integer totalSeats;
	private Integer availableSeats;
	private Integer movieId;
	private Integer theatreId;
	private String screenName;
	
}
