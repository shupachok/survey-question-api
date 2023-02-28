package com.supachok.springboot.restapi.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException{

	public QuestionNotFoundException(Long id) {
		super("id:"+id);
	}

}