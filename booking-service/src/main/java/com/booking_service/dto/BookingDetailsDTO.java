package com.booking_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDTO{
	private String bookingId;
	private Long showtimeId;
	private String userId;
	private List<String> seatIds;
	private String status;
	private BigDecimal amount;
	private Instant createdAt;
	private String theaterName;
	private String screenName;
	private String  movieName;
	private Instant showStartTime;
	private String moviePosterUrl;
}
	    
