package com.booking_service.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.booking_service.client.MovieClient;
import com.booking_service.client.ShowtimeDetailsDTO;
import com.booking_service.dto.BookingDetailsDTO;
import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;
import com.booking_service.entity.Booking;
import com.booking_service.kafka.BookingProducer;
import com.booking_service.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
	
	private final RedissonClient redissonClient;
	private final SeatLockService seatLockService;
	private final BookingRepository bookingRepository;
	private final MovieClient movieClient;
    private final IdGenerator idGenerator;
    private final BookingProducer bookingProducer;

	@Override
	public String holdKey(String holdId) {
		// TODO Auto-generated method stub
		return "hold:" + holdId; 
	}
	@Override
	public BookingResponseDTO confirm(String userId, String email, ConfirmBookingRequestDTO req) {
		// TODO Auto-generated method stub
		var bucket = redissonClient.<Map<String, Object>>getBucket(holdKey(req.getHoldId()));
        Map<String, Object> hold = bucket.get();
        if (hold == null) throw new NoSuchElementException("Hold expired or not found");

        String owner = (String) hold.get("userId");
        if (!userId.equals(owner)) {
            throw new SecurityException("Hold owned by another user");
        }

        Long showtimeId = ((Number) hold.get("showtimeId")).longValue();
//        System.out.println("showID: "+showtimeId);
        @SuppressWarnings("unchecked")
		List<String> seatIds = (List<String>) hold.get("seatIds");
        // persist booking first (idempotency is a later enhancement)
        String bookingId = idGenerator.newId("BOOK");
        Instant now =Instant.now();
        ShowtimeDetailsDTO detailsDTO = movieClient.getShowtimesById(showtimeId);
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setShowtimeId(showtimeId);
        booking.setUserId(userId);
        booking.setEmail(email);
        booking.setSeatIds(seatIds);
        booking.setCreatedAt(now);
        booking.setPaymentRef(req.getPaymentRef());
        booking.setAmount(req.getAmount());
        booking.setStatus("CONFIRMED");
        booking.setShowtime(detailsDTO.getStartTime());
        booking.setMovieName(detailsDTO.getMovieName());
        booking.setScreenName(detailsDTO.getScreenName());
        booking.setTheaterName(detailsDTO.getTheatreName());
        booking.setMoviePosterUrl(detailsDTO.getMoviePosterUrl());
//        Booking booking = new Booking(
//            bookingId, showtimeId, userId, seatIds, now, req.getPaymentRef(), "CONFIRMED",req.getAmount()
//        );
        bookingRepository.save(booking);

        // mark seats as booked in movie-service (source of truth for seats)
        movieClient.markSeatsBooked(showtimeId, seatIds);

        // clear hold + release locks
        bucket.delete();
        for (String seatId : seatIds) {
            seatLockService.unlockQuietly(seatLockService.getLock(showtimeId, seatId));
        }
        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setAmount(req.getAmount());
        responseDTO.setBookingId(bookingId);
        responseDTO.setCreatedAt(now);
        responseDTO.setMovieName(booking.getMovieName());
        responseDTO.setMoviePosterUrl(booking.getMoviePosterUrl());
        responseDTO.setScreenName(booking.getScreenName());
        responseDTO.setSeatIds(seatIds);
        responseDTO.setStatus("CONFIRMED");
        LocalDateTime localDateTime = booking.getShowtime();
		if(localDateTime!=null) {
			Instant instant = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant();
			responseDTO.setShowStartTime(instant);				
		}
		responseDTO.setTheaterName(booking.getTheaterName());
		responseDTO.setShowtimeId(showtimeId);
		responseDTO.setEmail(email);
		bookingProducer.sendBookingEvent(responseDTO);
		return responseDTO;
	}
	@Override
	public List<BookingResponseDTO> myBookings(String userId) {
		// TODO Auto-generated method stub
		List<Booking> booking = bookingRepository.findByUserId(userId);
		List <BookingResponseDTO>res = booking.stream().map(book->{
			BookingResponseDTO dto = new BookingResponseDTO();
			dto.setBookingId(book.getId());
			dto.setShowtimeId(book.getShowtimeId());
			dto.setStatus(book.getStatus());
			dto.setSeatIds(book.getSeatIds());
			dto.setAmount(book.getAmount());
			dto.setCreatedAt(book.getCreatedAt());
			dto.setMovieName(book.getMovieName());
			dto.setTheaterName(book.getTheaterName());
//			System.out.println("theater: "+dto.getTheaterName());
			dto.setScreenName(book.getScreenName());
			LocalDateTime localDateTime = book.getShowtime();
//			System.out.println("showTime: "+localDateTime);
			if(localDateTime!=null) {
				Instant instant = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant();
				dto.setShowStartTime(instant);				
			}
			//dto.setShowStartTime(book.getShowtime());
			dto.setMoviePosterUrl(book.getMoviePosterUrl());
			return dto;
		}).toList();
		return res;
	}
	@Override
	public BookingResponseDTO getBookingDetailsbyId(String bookingId) {
		// TODO Auto-generated method stub
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new IllegalArgumentException("Invalid booking Id"));
		BookingResponseDTO responseDTO = new BookingResponseDTO();
		responseDTO.setAmount(booking.getAmount());
        responseDTO.setBookingId(bookingId);
        responseDTO.setCreatedAt(booking.getCreatedAt());
        responseDTO.setMovieName(booking.getMovieName());
        responseDTO.setMoviePosterUrl(booking.getMoviePosterUrl());
        responseDTO.setScreenName(booking.getScreenName());
        responseDTO.setSeatIds(booking.getSeatIds());
        responseDTO.setStatus("CONFIRMED");
        LocalDateTime localDateTime = booking.getShowtime();
		if(localDateTime!=null) {
			Instant instant = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant();
			responseDTO.setShowStartTime(instant);				
		}
		responseDTO.setTheaterName(booking.getTheaterName());
		responseDTO.setShowtimeId(booking.getShowtimeId());
		return null;
	}

}
