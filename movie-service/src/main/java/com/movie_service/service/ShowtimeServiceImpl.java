package com.movie_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.movie_service.dto.ShowtimeRequestDTO;
import com.movie_service.dto.ShowtimeResponseDTO;
import com.movie_service.entity.Movie;
import com.movie_service.entity.Seat;
import com.movie_service.entity.SeatStatus;
import com.movie_service.entity.Showtime;
import com.movie_service.entity.Theatre;
import com.movie_service.exception.MovieServiceException;
import com.movie_service.exception.ShowtimeServiceException;
import com.movie_service.repository.MovieRepository;
import com.movie_service.repository.SeatRepository;
import com.movie_service.repository.ShowtimeRepository;
import com.movie_service.repository.TheatreRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService{
	
	private final ShowtimeRepository showtimeRepository;
	private final MovieRepository movieRepository;
	private final TheatreRepository theatreRepository;
	private final SeatRepository seatRepository;

	@Override
	@CacheEvict(value = "showtimes", allEntries = true)
	public void addShowtime(ShowtimeRequestDTO showtimeDto) {
		// TODO Auto-generated method stub
		boolean exists = showtimeRepository.existsByMovie_IdAndStartTime
				(showtimeDto.getMovieId(), showtimeDto.getStartTime());
		if(exists) {
			throw new ShowtimeServiceException("Showtime already exists for movie at given time");
		}
		Movie movie = movieRepository.findById(showtimeDto.getMovieId()).orElseThrow(
				()->new MovieServiceException("Please enter valid movieId"));
		Theatre theatre = theatreRepository.findById(showtimeDto.getTheatreId()).orElseThrow(
				()->new ShowtimeServiceException("Please enter valid Theatre ID"));
		Showtime showtime = new Showtime(); 
		showtime.setCreatedAt(LocalDateTime.now());
		showtime.setMovie(movie);
		showtime.setTheatre(theatre);
		showtime.setAvailableSeats(showtimeDto.getAvailableSeats());
		showtime.setScreenName(showtimeDto.getScreenName());
		showtime.setTotalSeats(showtimeDto.getTotalSeats());
		showtime.setStartTime(showtimeDto.getStartTime());
		 for (int row = 1; row <= 5; row++) {
	            for (int col = 1; col <= 10; col++) {
	                String seatNumber = (char)('A' + row - 1) + String.valueOf(col); // A1..A10, B1..B10
	                Seat seat = new Seat();
	                seat.setSeatNumber(seatNumber);
	                seat.setStatus(SeatStatus.AVAILABLE);
	                seat.setShowtime(showtime);
	                showtime.getSeats().add(seat);
	            }
	        }
		showtimeRepository.save(showtime);
	}

	@Override
	@Cacheable(value ="showtimes" ,key="'fromnow'")
	public List<ShowtimeResponseDTO> getShowtimesFromNow() {
		// TODO Auto-generated method stub
		 System.out.println("Loading showtimes from DB");
		LocalDateTime start= LocalDateTime.now();
		List<Showtime> showtimes = showtimeRepository.findByStartTimeAfter(start);
		List<ShowtimeResponseDTO> showtimeDto = showtimes.stream().map(showtime ->{
			ShowtimeResponseDTO dto = new ShowtimeResponseDTO();
			dto.setId(showtime.getId());
			dto.setAvailableSeats(showtime.getAvailableSeats());
			dto.setMovie(List.of(showtime.getMovie()));
			dto.setStartTime(showtime.getStartTime());
			dto.setTheatre(List.of(showtime.getTheatre()));
			dto.setTotalSeats(showtime.getTotalSeats());
			dto.setScreenName(showtime.getScreenName());
			return dto;
		}).collect(Collectors.toList());
		return showtimeDto;
	}

	@Override
	@Cacheable(value ="showtimes" ,key="'bymovie'")
	public List<Showtime> getShowtimesByMovie(Long movieId) {
		// TODO Auto-generated method stub
		return showtimeRepository.findByMovie_Id(movieId);
	}

	@Override
	@Cacheable(value ="showtimes" ,key="#date.toString()")
	public List<ShowtimeResponseDTO> getShowtimesByDate(LocalDate date) {
		// TODO Auto-generated method stub
		System.out.println("Load from DB"+date);
		LocalDateTime start;
		if(date.isEqual(LocalDate.now())) {
			start = LocalDateTime.now();
		}else {
			start = date.atStartOfDay();
		}
		LocalDateTime end = date.plusDays(1).atStartOfDay();
		List<Showtime> showtimes = showtimeRepository.findByStartTimeBetween(start, end);
		if(showtimes.isEmpty()) {
			throw new ShowtimeServiceException("Records not Available");
		}
		List<ShowtimeResponseDTO> showtimeDto = showtimes.stream().map(showtime->{
			ShowtimeResponseDTO dto = new ShowtimeResponseDTO();
			dto.setAvailableSeats(showtime.getAvailableSeats());
			dto.setId(showtime.getId());
			dto.setMovie(List.of(showtime.getMovie()));
			dto.setStartTime(showtime.getStartTime());
			dto.setTheatre(List.of(showtime.getTheatre()));
			dto.setTotalSeats(showtime.getTotalSeats());
			dto.setScreenName(showtime.getScreenName());
			return dto;
		}).collect(Collectors.toList());
		return showtimeDto;
	}

	@Override
	@CacheEvict(value = "showtimes", allEntries = true)
	public  void updateShowtime(Long id, ShowtimeRequestDTO showtimeDto) {
		// TODO Auto-generated method stub
		Showtime updateShowtime = showtimeRepository.findById(id).orElseThrow(
					()->new ShowtimeServiceException("Showtime not exists for given ID"));
		if(showtimeDto.getAvailableSeats()!=null) {			
			updateShowtime.setAvailableSeats(showtimeDto.getAvailableSeats());
		}
		if(showtimeDto.getMovieId()!=null) {
			Movie movie = movieRepository.findById(showtimeDto.getMovieId()).orElseThrow(
					()->new ShowtimeServiceException("Please enter valid Movie ID"));
			updateShowtime.setMovie(movie);			
		}
		//System.out.println(showtimeDto.getStartTime());
		if(showtimeDto.getStartTime()!=null) {
			updateShowtime.setStartTime(showtimeDto.getStartTime());			
		}
		if(showtimeDto.getTheatreId()!=null) {
			Theatre theatre = theatreRepository.findById(showtimeDto.getTheatreId()).orElseThrow(
					()->new ShowtimeServiceException("Please enter valid Theatre ID"));
			updateShowtime.setTheatre(theatre);			
		}
		if(showtimeDto.getTotalSeats()!=null) {
			updateShowtime.setTotalSeats(showtimeDto.getTotalSeats());			
		}
			updateShowtime.setUpdatedAt(LocalDateTime.now());
		showtimeRepository.save(updateShowtime);
	}

	@Override
	@CacheEvict(value = "showtimes", allEntries = true)
	public void deleteshowtime(Long id) {
		// TODO Auto-generated method stub
		showtimeRepository.findById(id).orElseThrow(()->new ShowtimeServiceException("Show time not available for delete"));
		showtimeRepository.deleteById(id);
		
	}

	@Override
	public Map<String, Boolean> checkSeatsAvailability(Long showtimeId, List<String> seatIds) {
		// TODO Auto-generated method stub
		Showtime showtime = showtimeRepository.findById(showtimeId)
		        .orElseThrow(() -> new RuntimeException("Showtime not found"));

		    Map<String, Boolean> availability = new HashMap<>();
		    for (String seatId : seatIds) {
		        Seat seat = showtime.getSeats().stream()
		                .filter(s -> s.getSeatNumber().equals(seatId))
		                .findFirst()
		                .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));
		        //availability.put(seatId, seat.isAvailable());
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
