package com.movie_service.repository;

import com.movie_service.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowtimeIdAndSeatNumberIn(Long showtimeId, List<String> seatNumbers);
    List<Seat> findByShowtime_Id(Long showtimeId);
}
