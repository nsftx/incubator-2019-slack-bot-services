package com.welcome.bot.exception.trigger;

import org.apache.coyote.http11.HttpOutputBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.exception.base.BaseException;


@SuppressWarnings("serial")
public class TriggerNotFoundException extends BaseException{
	
	public TriggerNotFoundException(Integer id) {
        super("Trigger with id: " + id + " not found.", HttpStatus.NOT_FOUND);
	}
	
}
