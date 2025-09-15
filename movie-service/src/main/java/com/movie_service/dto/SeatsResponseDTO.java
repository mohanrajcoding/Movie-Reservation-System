package com.movie_service.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatsResponseDTO {

	private Layout layout;
	private List<String> booked;
	private String movieTitle;
    private Instant showtime;
	private String screenName;
	private String theatreName;
	private String location;
}
