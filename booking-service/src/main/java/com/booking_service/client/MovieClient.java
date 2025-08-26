package com.booking_service.client;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.booking_service.config.MovieServiceProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovieClient {

	private final RestTemplate restTemplate;
	private final MovieServiceProperties props;
	
	public Map<String, Boolean> seatAvailability(Long showtimeId, List<String> seatIds){
		System.out.println("showTimeId: "+showtimeId);
		String url = props.getBaseUrl()+ "/api/movies/showtimes/" + showtimeId + "/seats/availability?seatIds=" +String.join(",", seatIds);
		System.out.println("url: "+url);
		ResponseEntity<Map> res = restTemplate.getForEntity(url, Map.class);
		return res.getBody()==null?Map.of():res.getBody();
	}
	
	public void markSeatsBooked (Long showtimeId, List<String> seatIds) {
		String url = props.getBaseUrl() + "/api/movies/showtimes/" + showtimeId + "/seats/mark-booked";
		restTemplate.postForEntity(url, seatIds, Void.class);
	}
	
	
}
