package com.welcome.bot.exception.schedule;

import org.springframework.http.HttpStatus;
import com.welcome.bot.exception.base.BaseException;

@SuppressWarnings("serial")
public class ScheduleNotFoundException extends BaseException{
	public ScheduleNotFoundException(Integer id) {
        super("Schedule with id: " + id + " not found.", HttpStatus.NOT_FOUND);
	}
}
