package com.movie_service.service;

import java.util.List;

import com.movie_service.entity.Theatre;

public interface TheatreService {

	void addTheatre(Theatre theatre);
	void updateTheatreById(Theatre theatre);
	Theatre getTheatreById(Long id);
	List<Theatre> getTheatres();

}
