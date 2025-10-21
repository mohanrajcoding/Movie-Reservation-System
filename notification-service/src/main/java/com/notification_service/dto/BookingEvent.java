package com.notification_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {
	private String bookingId;
	private Long showtimeId;
	private String userId;
	private String email;
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
