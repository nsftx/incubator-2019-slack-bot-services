package com.welcome.bot.exception.trigger;

import org.springframework.http.HttpStatus;

import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class TriggerValidationException extends BaseException{
	
	public TriggerValidationException(String input, String message) {
        super("Error with input: "+ input + "; " + message, HttpStatus.BAD_REQUEST);
	}
}
