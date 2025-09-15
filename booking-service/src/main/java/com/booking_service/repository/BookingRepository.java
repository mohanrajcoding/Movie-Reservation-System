package com.booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking_service.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String>{
 List<Booking> findByUserId(String userId);
}
