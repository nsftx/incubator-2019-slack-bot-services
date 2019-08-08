package com.welcome.bot.exception;

import java.io.IOException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	//message handler exceptions
	
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(MessageNotFoundException ex) throws IOException {
    	CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.NOT_FOUND);
    	apiError.setMessage(ex.getMessage());
    	return buildResponseEntity(apiError);
    }
    
	@ExceptionHandler(MessageValidationException.class)
    	protected ResponseEntity<Object> handleConstraintViolation(MessageValidationException ex, WebRequest request) {
		CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
    	apiError.setMessage(ex.getMessage());
    	apiError.setPath(request.getDescription(false));
    	return buildResponseEntity(apiError);
	}

	
	//trigger handler exceptions
	
    @ExceptionHandler(TriggerNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(TriggerNotFoundException ex) throws IOException {
    	CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.NOT_FOUND);
    	apiError.setMessage(ex.getMessage());
    	return buildResponseEntity(apiError);
    }
    
	@ExceptionHandler(TriggerValidationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(TriggerValidationException ex, WebRequest request) {
	CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
	apiError.setMessage(ex.getMessage());
	apiError.setPath(request.getDescription(false));
	return buildResponseEntity(apiError);
	}
	
	//schedule handler exception
	
	@ExceptionHandler(ScheduleValidationException.class)
		protected ResponseEntity<Object> handleConstraintViolation(TriggerNotFoundException ex) {
		CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(ScheduleNotFoundException ex) throws IOException {
    	CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.NOT_FOUND);
    	apiError.setMessage(ex.getMessage());
    	return buildResponseEntity(apiError);
    }
	
    //global exception handler exceptions
    
	 @ExceptionHandler(javax.validation.ConstraintViolationException.class)
	    protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
	    CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
	    apiError.setMessage("Validation error -javax");
	    return buildResponseEntity(apiError);
	 }
	 
	 @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
	    protected ResponseEntity<Object> handleConstraintViolation(org.hibernate.exception.ConstraintViolationException ex) {
	    CustomExceptionPayload apiError = new CustomExceptionPayload(HttpStatus.BAD_REQUEST);
	    apiError.setMessage("Validation error -hibernate");
	    return buildResponseEntity(apiError);
	 }
	 
	 
	 private ResponseEntity<Object> buildResponseEntity(CustomExceptionPayload apiError) {
		 return new ResponseEntity<>(apiError, apiError.getStatus());
	 }
}
