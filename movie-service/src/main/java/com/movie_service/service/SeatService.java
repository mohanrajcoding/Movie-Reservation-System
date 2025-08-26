package com.movie_service.service;

import java.util.List;
import java.util.Map;

public interface SeatService {
	//List<Seat> checkSeatAvailability(Long showtimeId, List<String> seatNumbers);
    //public void bookSeats(Long showtimeId, List<String> seatNumbers);
	Map<String, Boolean> checkSeatsAvailability(Long showtimeId, List<String> seatIds);
	void markSeatsAsBooked(Long showtimeId, List<String> seatIds);
}
