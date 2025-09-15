package com.booking_service.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.booking_service.client.MovieClient;
import com.booking_service.dto.CreateHoldRequestDTO;
import com.booking_service.dto.CreateHoldResponseDTO;

@Service
public class SeatHoldServiceImpl implements SeatHoldService{
	
	@Autowired
	private final MovieClient movieClient;
	private final SeatLockService seatLockService;
	private final RedissonClient redissonClient;
    private final IdGenerator idGenerator;
    private final long holdTtlMinutes;

    public SeatHoldServiceImpl(
        MovieClient movieClient,
        SeatLockService seatLockService,
        RedissonClient redissonClient,
        IdGenerator idGenerator,
        @Value("${booking.hold-ttl-minutes}") long holdTtlMinutes
    ) {
        this.movieClient = movieClient;
        this.seatLockService = seatLockService;
        this.redissonClient = redissonClient;
        this.idGenerator = idGenerator;
        this.holdTtlMinutes = holdTtlMinutes;
    }

	@Override
	public String holdKey(String holdId) {
		// TODO Auto-generated method stub
		return "hold:" + holdId; 
	}

	@SuppressWarnings("deprecation")
	@Override
	public CreateHoldResponseDTO createHold(String userId, CreateHoldRequestDTO req) {
		// TODO Auto-generated method stub
		System.out.println("ShowId: "+req.getShowtimeId());
		Map<String, Boolean> availability = movieClient.seatAvailability(req.getShowtimeId(), req.getSeats());
		List<String> unavailable = req.getSeats().stream().filter(s-> !availability.getOrDefault(s, false)).toList();
		if (!unavailable.isEmpty()) {
            throw new IllegalStateException("Seats not available: " + unavailable);
        }

        List<RLock> acquired = new ArrayList<>();
        try {
            for (String seatId : req.getSeats()) {
                RLock lock = seatLockService.getLock(req.getShowtimeId(), seatId);
                if (!seatLockService.tryLock(lock, holdTtlMinutes)) {
                    throw new IllegalStateException("Seat locked by another user: " + seatId);
                }
                acquired.add(lock);
            }

            String holdId = idGenerator.newId("HOLD");
            Map<String, Object> holdData = new HashMap<>();
            holdData.put("showtimeId", req.getShowtimeId());
            holdData.put("userId", userId);
            holdData.put("seatIds", req.getSeats());

            RBucket<Map<String, Object>> bucket = redissonClient.getBucket(holdKey(holdId));
            bucket.set(holdData, 
            	    holdTtlMinutes, 
            	    TimeUnit.MINUTES);
            
            System.out.println("Hold stored in Redis with key: " + holdKey(holdId));
            System.out.println("TTL (minutes): " + holdTtlMinutes);
            System.out.println("Value: " + bucket.get());

            long expiresAt = Instant.now().plusSeconds(holdTtlMinutes * 60).toEpochMilli();
            return new CreateHoldResponseDTO(holdId, req.getShowtimeId(), userId, req.getSeats(), expiresAt);

        } catch (RuntimeException e) {
            for (RLock lock : acquired) seatLockService.unlockQuietly(lock);
            throw e;
        }
    };
    
	@Override
	public Map<String, Object> getHold(String holdId) {
		// TODO Auto-generated method stub
		var bucket = redissonClient.<Map<String, Object>>getBucket(holdKey(holdId));
        Map<String, Object> data = bucket.get();
        if (data == null) throw new NoSuchElementException("Hold not found or expired");
        return data;
	}

	@Override
	public void cancelHold(String userId, String holdId) {
		// TODO Auto-generated method stub
		Map<String, Object> hold = getHold(holdId);
        if (!Objects.equals(hold.get("userId"), userId)) {
            throw new SecurityException("Hold owned by another user");
        }
        Long showtimeId = ((Number) hold.get("showtimeId")).longValue();
        @SuppressWarnings("unchecked")
        List<String> seatIds = (List<String>) hold.get("seatIds");

        redissonClient.getBucket(holdKey(holdId)).delete();
        for (String seatId : seatIds) {
            seatLockService.unlockQuietly(seatLockService.getLock(showtimeId, seatId));
        }
    }
		
	}