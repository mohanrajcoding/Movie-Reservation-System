package com.movie_service.service;

import java.util.List;

import com.movie_service.entity.Theatre;

public interface TheatreService {

	void addTheatre(Theatre theatre);
	void updateTheatre(Theatre theatre);
	Theatre getTheatreById(Integer id);
	List<Theatre> getTheatres();
	

}
