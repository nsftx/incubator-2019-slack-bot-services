package com.welcome.bot.exception.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class MessageValidationException extends BaseException {
	public MessageValidationException(String input) {
		super("Message validation error for : " + input
				+ ". Message text needs more than 20 characters and message title should be between 5 and 30 characters ",
				HttpStatus.BAD_REQUEST);
	}

	public MessageValidationException(String input, String message) {
		super("Error with input: " + input + "; " + message, HttpStatus.BAD_REQUEST);
	}
}