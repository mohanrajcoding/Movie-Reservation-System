package com.movie_service.exception;

import java.util.function.Supplier;

public class ShowtimeServiceException extends RuntimeException {

	public ShowtimeServiceException(String message) {
		super(message);
	}

}
