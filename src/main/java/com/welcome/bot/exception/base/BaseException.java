package com.welcome.bot.exception.base;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

	private Date timestamp;
	private HttpStatus status;
	private Integer error;
	private String path;

	public BaseException(String message, Throwable cause) {
		super(message, cause);
		this.timestamp = new Date();
	}

	public BaseException(String message) {
		super(message);
		this.timestamp = new Date();
	}

	public BaseException(String message, HttpStatus status) {
		super(message);
		this.timestamp = new Date();
		this.status = status;
		this.error = status.value();
	}

	public BaseException(String message, HttpStatus status, String path) {
		super(message);
		this.timestamp = new Date();
		this.status = status;
		this.error = status.value();
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getPath() {
		return path;
	}

	public Integer getError() {
		return this.error;
	}
}
