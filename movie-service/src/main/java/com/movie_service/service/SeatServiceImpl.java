package com.movie_service.service;

import com.movie_service.entity.Seat;
import com.movie_service.entity.SeatStatus;
import com.movie_service.entity.Showtime;
import com.movie_service.repository.SeatRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
	public Map<String, Boolean> checkSeatsAvailability(Long showtimeId, List<String> seatNumbers) {
		// TODO Auto-generated method stub
		List<Seat> seats = seatRepository.findByShowtimeIdAndSeatNumberIn(showtimeId,seatNumbers);
		
		    Map<String, Boolean> availability = new HashMap<>();
		    for (String seatId : seatNumbers) {
//		        Seat seat = showtime.getSeats().stream()
//		                .filter(s -> s.getSeatNumber().equals(seatId))
//		                .findFirst()
//		                .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));
		        //availability.put(seatId, seat.isAvailable());
		    	Seat seat = seats.stream()
		    			.filter(s->s.getSeatNumber().equals(seatId))
		    			.findFirst()
		    			.orElseThrow(()->new RuntimeException("Seat not found: " + seatId));
		        availability.put(seatId, seat.getStatus()==SeatStatus.AVAILABLE);
		    }
		    return availability;
	}

	@Transactional
	public void markSeatsAsBooked(Long showtimeId, List<String> seatIds) {
		List<Seat> seats = seatRepository.findByShowtimeIdAndSeatNumberIn(showtimeId, seatIds);

        for (Seat seat : seats) {
            if (seat.getStatus() == SeatStatus.BOOKED) {
                throw new IllegalStateException("Seat already booked: " + seat.getSeatNumber());
            }
            seat.setStatus(SeatStatus.BOOKED);
        }

        seatRepository.saveAll(seats);
	}
}
