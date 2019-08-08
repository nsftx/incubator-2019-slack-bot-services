package com.welcome.bot.exception;

@SuppressWarnings("serial")
public class ScheduleNotFoundException extends RuntimeException{
	public ScheduleNotFoundException(Integer id) {
        super("Message with id: " + id + " not found.");
	}
}
