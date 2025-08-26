package com.booking_service.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.booking_service.client.MovieClient;
import com.booking_service.dto.BookingResponseDTO;
import com.booking_service.dto.ConfirmBookingRequestDTO;
import com.booking_service.entity.Booking;
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
	@Override
	public String holdKey(String holdId) {
		// TODO Auto-generated method stub
		return "hold:" + holdId; 
	}
	@Override
	public BookingResponseDTO confirm(String userId, ConfirmBookingRequestDTO req) {
		// TODO Auto-generated method stub
		var bucket = redissonClient.<Map<String, Object>>getBucket(holdKey(req.holdId()));
        Map<String, Object> hold = bucket.get();
        if (hold == null) throw new NoSuchElementException("Hold expired or not found");

        String owner = (String) hold.get("userId");
        if (!userId.equals(owner)) {
            throw new SecurityException("Hold owned by another user");
        }

        Long showtimeId = ((Number) hold.get("showtimeId")).longValue();
        List<String> seatIds = (List<String>) hold.get("seatIds");

        // persist booking first (idempotency is a later enhancement)
        String bookingId = idGenerator.newId("BOOK");
        Booking booking = new Booking(
            bookingId, showtimeId, userId, seatIds, Instant.now(), req.paymentReference(), "CONFIRMED"
        );
        bookingRepository.save(booking);

        // mark seats as booked in movie-service (source of truth for seats)
        movieClient.markSeatsBooked(showtimeId, seatIds);

        // clear hold + release locks
        bucket.delete();
        for (String seatId : seatIds) {
            seatLockService.unlockQuietly(seatLockService.getLock(showtimeId, seatId));
        }

        return new BookingResponseDTO(bookingId, showtimeId, userId, seatIds, "CONFIRMED");
	}

}
