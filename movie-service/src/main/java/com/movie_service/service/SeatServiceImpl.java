package com.movie_service.service;

import com.movie_service.entity.Seat;
import com.movie_service.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public List<Seat> checkSeatAvailability(Long showtimeId, List<String> seatNumbers) {
        return seatRepository.findByShowtimeIdAndSeatNumberIn(showtimeId, seatNumbers);
    }

    public void bookSeats(Long showtimeId, List<String> seatNumbers) {
        List<Seat> seats = seatRepository.findByShowtimeIdAndSeatNumberIn(showtimeId, seatNumbers);

        for (Seat seat : seats) {
            if (!seat.isAvailable()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already booked");
            }
            seat.setAvailable(false);
        }
        seatRepository.saveAll(seats);
    }
}
