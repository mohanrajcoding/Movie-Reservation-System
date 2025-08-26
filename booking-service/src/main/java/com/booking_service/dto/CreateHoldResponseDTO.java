package com.booking_service.dto;

import java.util.List;

public record CreateHoldResponseDTO (
		String holdId,
		Long showtimeId,
		String userId,
		List <String> seatIds,
		long expiresAtEpochMillis
		)
{

	
}
