package com.movie_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_service.entity.Showtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer>{

	List<Showtime> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
	List<Showtime> findByStartTimeAfter(LocalDateTime start);
	List<Showtime> findByMovie_Id(Integer movieId);
	boolean existsByMovie_IdAndStartTime(Integer movieId, LocalDateTime startstartTime);
	boolean existsById(Integer movieId);
}
