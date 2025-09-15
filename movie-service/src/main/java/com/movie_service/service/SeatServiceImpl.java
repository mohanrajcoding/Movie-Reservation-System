package com.movie_service.service;

import com.movie_service.dto.Layout;
import com.movie_service.dto.SeatsResponseDTO;
import com.movie_service.entity.Seat;
import com.movie_service.entity.SeatStatus;
import com.movie_service.entity.Showtime;
import com.movie_service.exception.MovieServiceException;
import com.movie_service.repository.SeatRepository;
import com.movie_service.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;

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
            if (seat.getStatus() == SeatStatus.CONFIRMED) {
                throw new IllegalStateException("Seat already booked: " + seat.getSeatNumber());
            }
            seat.setStatus(SeatStatus.CONFIRMED);
        }

        seatRepository.saveAll(seats);
	}

	@Override
	public SeatsResponseDTO seatsForShowtimeId(Long showtimeId) {
		// TODO Auto-generated method stub
		List<Seat> seats = seatRepository.findByShowtime_Id(showtimeId);
		Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(()-> new MovieServiceException("Show details not available"));
		int maxRow = seats.stream()
                .mapToInt(seat -> seat.getSeatNumber().charAt(0) - 'A' + 1)
                .max().orElse(0);

        int maxCol = seats.stream()
                .mapToInt(seat -> Integer.parseInt(seat.getSeatNumber().substring(1)))
                .max().orElse(0);
        List <String> bookedSeats = seats.stream().filter(seat->seat.getStatus().equals(SeatStatus.CONFIRMED))
        							.map(Seat::getSeatNumber).toList();
        SeatsResponseDTO responseDTO = new SeatsResponseDTO();
        responseDTO.setLayout(new Layout(maxRow,maxCol));
        responseDTO.setBooked(bookedSeats);
        responseDTO.setLocation(showtime.getTheatre().getLocation());
        responseDTO.setMovieTitle(showtime.getMovie().getTitle());
        responseDTO.setScreenName(showtime.getScreenName());
        responseDTO.setTheatreName(showtime.getTheatre().getName());
        LocalDateTime localDateTime = showtime.getStartTime();
        if(localDateTime!=null) {
			Instant instant = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant();
			responseDTO.setShowtime(instant);				
		}
		return responseDTO;
	}
}
