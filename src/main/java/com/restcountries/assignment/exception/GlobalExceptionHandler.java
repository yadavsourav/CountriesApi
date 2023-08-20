package com.restcountries.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	  @ExceptionHandler(PageNotFoundException.class) 
	  public ResponseEntity<ErrorResponse> handleApiException(PageNotFoundException ex) {
	      ErrorResponse errorResponse = new ErrorResponse(400, ex.getMessage()); 
	  return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST); 
	  }
	 
    
    @ExceptionHandler(CountryNotFoundException.class)
	public ResponseEntity<ErrorResponse> countryNotFoundExceptionHandler(
			CountryNotFoundException ex) {
		
		
		ErrorResponse errorResponse = new ErrorResponse(404, ex.getMessage());
											 
    	return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Add more @ExceptionHandler methods for other types of exceptions
}