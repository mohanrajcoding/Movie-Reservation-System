package com.booking_service.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "movie-service",
				url = "${movie.service.base-url}")
public interface MovieClient {

	//private final RestTemplate restTemplate;
//	private final MovieServiceProperties props;
	
	@GetMapping("/api/movies/seats/{showtimeId}/availability")
	public Map<String, Boolean> seatAvailability(@PathVariable Long showtimeId, 
													@RequestParam List<String> seatIds);
	@PostMapping("/api/movies/seats/{showtimeId}/mark-booked")
	public void markSeatsBooked (@PathVariable Long showtimeId, 
									@RequestBody List<String> seatIds);
	
	
}
