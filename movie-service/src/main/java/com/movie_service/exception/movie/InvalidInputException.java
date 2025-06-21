package com.movie_service.exception.movie;

import org.apache.logging.log4j.message.Message;

public class InvalidInputException extends RuntimeException {

	public InvalidInputException (String message) {
		super(message);
	}
}
