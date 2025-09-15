package com.booking_service.client;

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
	private String theatreName;
	private String moviePosterUrl;
	private String screenName;
}