package com.welcome.bot.exception;

@SuppressWarnings("serial")
public class MessageValidationException extends RuntimeException{
	
	public MessageValidationException(String input) {
	        super("Message validation error for : " + input + 
	        	  ". Message text needs more than 20 characters and message title should be between 5 and 30 characters ");
	}
	public MessageValidationException(String input, String message) {
        super("Error with input: "+ input + "; " + message);
	}
}