package com.welcome.bot.exception.schedule;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class ScheduleValidationException extends BaseException{
	public ScheduleValidationException(Date input, Date now) {
        super("Date " + input + " is in the past < " + now, HttpStatus.BAD_REQUEST);
	}
}
