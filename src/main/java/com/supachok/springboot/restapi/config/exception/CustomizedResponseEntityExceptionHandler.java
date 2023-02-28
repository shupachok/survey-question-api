package com.supachok.springboot.restapi.config.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.supachok.springboot.restapi.constant.MessageConstant;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;
    
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@ExceptionHandler(SurveyNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleSurveyNotFoundException(Exception ex, WebRequest request,Locale locale) throws Exception {
        String errorMessage = messageSource.getMessage(
                MessageConstant.SURVEY_NOT_EXIST, new Object[]{ex.getMessage()},locale);  
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				errorMessage, request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(QuestionNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleQuestionNotFoundException(Exception ex, WebRequest request,Locale locale) throws Exception {
        String errorMessage = messageSource.getMessage(
                MessageConstant.QUESTION_NOT_EXIST, new Object[]{ex.getMessage()},locale);  
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				errorMessage, request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> listErrorMessage = ex.getFieldErrors()
										  .stream()
										  .map(e -> e.getDefaultMessage())
										  .toList();
		
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				String.join(", ", listErrorMessage), request.getDescription(false));
		
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
}