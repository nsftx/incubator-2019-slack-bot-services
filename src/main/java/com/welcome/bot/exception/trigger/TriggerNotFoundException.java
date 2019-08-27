package com.welcome.bot.exception.trigger;


import org.springframework.http.HttpStatus;
import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class TriggerNotFoundException extends BaseException{
	
	public TriggerNotFoundException(Integer id) {
        super("Trigger with id: " + id + " not found.", HttpStatus.NOT_FOUND);
	}
	
	public TriggerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
	}
}
