package com.booking_service.service;

import java.util.List;

import com.booking_service.dto.BookingDetailsDTO;
import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;

public interface BookingService {

	String holdKey(String holdId);
	BookingResponseDTO confirm(String userId, String email, ConfirmBookingRequestDTO req);
	List<BookingResponseDTO> myBookings(String userId);
	BookingResponseDTO getBookingDetailsbyId(String bookingId);
}
