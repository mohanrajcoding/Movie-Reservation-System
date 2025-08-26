package com.booking_service.service;

import org.redisson.api.RLock;

public interface SeatLockService {

	String key(Long showtimeId, String seatId);
	RLock getLock(Long showtimeId, String seatId);
	boolean tryLock(RLock lock, long leaseMinutes);
	void unlockQuietly(RLock lock);
}
