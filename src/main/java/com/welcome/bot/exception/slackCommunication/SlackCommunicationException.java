package com.welcome.bot.exception.slackCommunication;

import java.lang.reflect.Constructor;

import org.springframework.http.HttpStatus;

import com.welcome.bot.exception.base.BaseException;

public class SlackCommunicationException extends BaseException{

	public SlackCommunicationException(String message, HttpStatus status) {
		super("Somethings wrong with Slack communication: ", HttpStatus.BAD_REQUEST);
	}

	public SlackCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SlackCommunicationException(String message) {
		super(message);
		
	}
	
}
