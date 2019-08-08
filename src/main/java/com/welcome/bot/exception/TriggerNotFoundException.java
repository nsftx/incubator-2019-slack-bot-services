package com.welcome.bot.exception;

@SuppressWarnings("serial")
public class TriggerNotFoundException extends RuntimeException{
	
	public TriggerNotFoundException(Integer id) {
        super("Trigger with id: " + id + " not found.");
	}
	
}
