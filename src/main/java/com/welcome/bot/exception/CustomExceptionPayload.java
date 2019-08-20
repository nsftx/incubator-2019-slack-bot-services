package com.welcome.bot.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.welcome.bot.exception.base.BaseException;

public class CustomExceptionPayload {
	private HttpStatus status;
	private Integer error;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") 
	private Date timestamp;
	private String message;
	private String path;

	CustomExceptionPayload() {
		this.timestamp = new Date();
	}

	CustomExceptionPayload(BaseException ex) {
		this.timestamp = ex.getTimestamp();
		this.message = ex.getMessage();
		this.status = ex.getStatus();
		this.error = ex.getStatus().value();
	}

	CustomExceptionPayload(HttpStatus status) {
		this();
		this.status = status;
		this.error = status.value();
		this.timestamp = new Date();
	}

	CustomExceptionPayload(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.error = status.value();
		this.message = "original message: " + ex.getMessage();
		this.timestamp = new Date();
	}

	CustomExceptionPayload(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.error = status.value();
		this.message = message;
		this.timestamp = new Date();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
