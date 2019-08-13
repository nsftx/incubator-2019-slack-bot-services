package com.welcome.bot.exception.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.exception.base.BaseException;


@SuppressWarnings("serial")
public class MessageNotFoundException extends BaseException{

	public MessageNotFoundException(Integer id) {
	        super("Message with id: " + id + " not found.", HttpStatus.NOT_FOUND);
	}
	
}
