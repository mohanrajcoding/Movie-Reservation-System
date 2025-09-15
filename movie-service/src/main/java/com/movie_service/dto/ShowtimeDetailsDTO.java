package com.movie_service.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeDetailsDTO {

	private Long  id;
	private LocalDateTime startTime;
	private String movieName;
	private String moviePosterUrl;
	private String theatreName;
	private String screenName;
}
