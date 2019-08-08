package com.welcome.bot.exception;

import java.util.Date;

@SuppressWarnings("serial")
public class ScheduleValidationException extends RuntimeException{
	public ScheduleValidationException(Date input, Date now) {
        super("Date " + input + " is in the past < " + now);
	}
}
