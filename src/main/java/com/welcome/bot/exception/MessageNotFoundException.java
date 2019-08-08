package com.welcome.bot.exception;

@SuppressWarnings("serial")
public class MessageNotFoundException extends RuntimeException{

	public MessageNotFoundException(Integer id) {
	        super("Message with id: " + id + " not found.");
	}
}
