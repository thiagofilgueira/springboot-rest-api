package com.mysample.error;

/**
 * @author thiagofilgueira
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long id) {
		super("Could not find user " + id);
	}

}