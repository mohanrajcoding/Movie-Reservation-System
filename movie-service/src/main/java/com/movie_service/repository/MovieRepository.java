package com.movie_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie_service.entity.Movie;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

	List<Movie> findByNormalizedTitle(String normalizedTitle);
}
