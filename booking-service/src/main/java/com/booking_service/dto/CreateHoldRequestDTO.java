package com.booking_service.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateHoldRequestDTO {

	@NotNull(message = "showtimeId is required")
	private Long showtimeId;
	
	@NotEmpty(message = "seatIds cannot be empty")
	private List<String> seats;
}
