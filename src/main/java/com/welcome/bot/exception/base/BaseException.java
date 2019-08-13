package com.welcome.bot.exception.base;

import java.util.Date;

import org.springframework.http.HttpStatus;
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BaseException extends RuntimeException {
 public BaseException() {
        
    }
	
    public BaseException(String message) {
        super(message);
    }


}
