package com.welcome.bot.exception;

@SuppressWarnings("serial")
public class TriggerValidationException extends RuntimeException{
	
	public TriggerValidationException(String input, String message) {
        super("Error with input: "+ input + "; " + message);
	}
	
}
