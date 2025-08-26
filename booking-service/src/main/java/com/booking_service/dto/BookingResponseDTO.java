package com.booking_service.dto;

import java.util.List;

public record BookingResponseDTO(
	    String bookingId,
	    Long showtimeId,
	    String userId,
	    List<String> seatIds,
	    String status
	) {}