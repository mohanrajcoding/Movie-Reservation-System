package com.movie_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie_service.entity.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

	List<Theatre> findByName(String name);
	boolean existsByName(String name);
	Theatre findByNameAndLocation(String name, String location);
	List<Theatre> findByNameAndLocationIgnoreCase(String name, String location);
}
