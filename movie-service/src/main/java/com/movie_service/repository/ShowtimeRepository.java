package com.movie_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_service.entity.Showtime;
@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long>{

}
