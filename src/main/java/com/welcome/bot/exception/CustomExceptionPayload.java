package com.welcome.bot.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import io.swagger.models.Path;


public class CustomExceptionPayload {
	 	private HttpStatus status;
	 	private Integer error;
	    private Date timestamp;
	    private String message;
	    private String path;

	    private CustomExceptionPayload() {
	        timestamp = new Date();
	    }

	    CustomExceptionPayload(HttpStatus status) {
	        this();
	        this.status = status;
	        this.error = status.value();
	    }

	    CustomExceptionPayload(HttpStatus status, Throwable ex) {
	        this();
	        this.status = status;
	        this.error = status.value();
	        this.message = "Unexpected error";
	    }

	    CustomExceptionPayload(HttpStatus status, String message, Throwable ex) {
	        this();
	        this.status = status;
	        this.error = status.value();
	        this.message = message;
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
