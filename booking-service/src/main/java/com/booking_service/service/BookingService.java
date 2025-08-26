package com.booking_service.service;

import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;

public interface BookingService {

	String holdKey(String holdId);
	BookingResponseDTO confirm(String userId, ConfirmBookingRequestDTO req);
}
