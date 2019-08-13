package com.welcome.bot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.welcome.bot.exception.base.BaseException;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> springHandleBaseException(BaseException ex, WebRequest request){
		CustomExceptionPayload exceptionPayload = new CustomExceptionPayload(ex);
		
		String path = ((ServletWebRequest)request).getRequest().getRequestURI();
		exceptionPayload.setPath(path);
		
		return buildResponseEntity(exceptionPayload);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error -javax");
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(
			org.hibernate.exception.ConstraintViolationException ex) {
		CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error -hibernate");
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(CustomExceptionPayload apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
