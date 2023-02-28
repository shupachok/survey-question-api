package com.supachok.springboot.restapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SurveyNotFoundException extends RuntimeException{

	public SurveyNotFoundException(Long id) {
		super("id:"+id);
	}

}