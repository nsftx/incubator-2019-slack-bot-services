package com.welcome.bot.slack.api.customexceptionhandler;

@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {
	
	public InvalidArgumentException(String errorMessage) {
		super(errorMessage);
	}
	
	public InvalidArgumentException(String errorMessage, Throwable error) {
		super(errorMessage,error);
	}
}