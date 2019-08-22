package com.welcome.bot.exception.user;

import org.springframework.http.HttpStatus;

import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class UserNotFoundException extends BaseException{
	
	public UserNotFoundException(Long id) {
        super("User with id: " + id + " not found.", HttpStatus.NOT_FOUND);
	}
	
	public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
	}
}

