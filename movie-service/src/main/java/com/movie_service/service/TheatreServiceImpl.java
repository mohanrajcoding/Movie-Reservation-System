package com.movie_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.movie_service.entity.Theatre;
import com.movie_service.exception.TheatreServiceException;
import com.movie_service.repository.TheatreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService{

	public final TheatreRepository theatreRepository;
	
	@Override
	public void addTheatre(Theatre theatre) {
		// TODO Auto-generated method stub
		List<Theatre> existingTheatre = theatreRepository.findByName(theatre.getName());
		boolean theatreExists = existingTheatre.stream().anyMatch(theatres->theatres.getName().equalsIgnoreCase(theatre.getName())&& 
				theatres.getLocation().equalsIgnoreCase(theatre.getLocation()));
		if(theatreExists) {
			throw new TheatreServiceException("Record Already exist..!");
		} else {
			theatreRepository.save(theatre);			
		}
	}

	@Override
	public void updateTheatre(Theatre theatre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Theatre getTheatreById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Theatre> getTheatres() {
		// TODO Auto-generated method stub
		return null;
	}

}
