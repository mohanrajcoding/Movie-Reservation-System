package com.booking_service.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SeatLockServiceImpl implements SeatLockService {

    private final RedissonClient redissonClient;
    private final long lockWaitSeconds;

    public SeatLockServiceImpl(
            RedissonClient redissonClient,
            @Value("${booking.lock-wait-seconds}") long lockWaitSeconds
    ) {
        this.redissonClient = redissonClient;
        this.lockWaitSeconds = lockWaitSeconds;
    }

    @Override
    public String key(Long showtimeId, String seatId) {
        return "lock:seat:" + showtimeId + ":" + seatId;
    }

    @Override
    public RLock getLock(Long showtimeId, String seatId) {
        return redissonClient.getLock(key(showtimeId, seatId));
    }

    @Override
    public boolean tryLock(RLock lock, long leaseMinutes) {
        try {
            return lock.tryLock(lockWaitSeconds, leaseMinutes, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public void unlockQuietly(RLock lock) {
        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception ignored) {}
    }
}
