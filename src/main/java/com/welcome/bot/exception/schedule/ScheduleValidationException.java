package com.welcome.bot.exception.schedule;



import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class ScheduleValidationException extends BaseException{
	public ScheduleValidationException(Date date) {
        super("Date: " + date + " is either older than present date or is greather than 120 days", HttpStatus.BAD_REQUEST);
	}
}
