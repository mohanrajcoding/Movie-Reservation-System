package com.movie_service.service;

import java.util.List;

import com.movie_service.entity.Seat;

public interface SeatService {
	List<Seat> checkSeatAvailability(Long showtimeId, List<String> seatNumbers);
    public void bookSeats(Long showtimeId, List<String> seatNumbers);
}
