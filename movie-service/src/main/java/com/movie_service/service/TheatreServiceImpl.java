package com.movie_service.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@CacheEvict(key = "theatre", allEntries = true)
	public void addTheatre(Theatre theatre) {
		// TODO Auto-generated method stub
		if(theatre.getLocation()==null) 
			throw new TheatreServiceException("Please enter the Location");
		if(theatre.getName()==null)
			throw new TheatreServiceException("Please enter the Theatre Name");
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
	@CacheEvict(key = "theatre", allEntries = true)
	public void updateTheatreById(Theatre theatre) {
		// TODO Auto-generated method stub
		Theatre existingTheatre = theatreRepository.findById(theatre.getId()).orElseThrow(()-> new TheatreServiceException("Theatre not available for update..!"));
		existingTheatre.setLocation(theatre.getLocation());
		existingTheatre.setName(theatre.getName());
		theatreRepository.save(existingTheatre);
	}

	@Override
	@Cacheable(key = "theatre", value = "byid")
	public Theatre getTheatreById(Long id) {
		// TODO Auto-generated method stub
		return theatreRepository.findById(id).orElseThrow(()-> new TheatreServiceException("Theatre not available for given Id..!"));
	}

	@Override
	@Cacheable(key = "theatre", value = "all")
	public List<Theatre> getTheatres() {
		// TODO Auto-generated method stub
		return theatreRepository.findAll();
	}

}
