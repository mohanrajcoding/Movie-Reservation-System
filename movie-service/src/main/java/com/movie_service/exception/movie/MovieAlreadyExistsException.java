package com.movie_service.exception.movie;

public class MovieAlreadyExistsException extends RuntimeException {

	public MovieAlreadyExistsException(String message) {
		super(message);
	}
}
