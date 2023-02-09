package com.supachok.springboot.restapi.dto;

import java.util.List;

public class QuestionDto {
	
	Long id;
	String description;
	
	String correctAnswer;
	List<OptionDto> options;
	public QuestionDto() {
	}

	
	public QuestionDto(Long id, String description, String correctAnswer,List<OptionDto> options) {
		super();
		this.id = id;
		this.description = description;
		this.correctAnswer = correctAnswer;
		this.options = options;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCorrectAnswer() {
		return correctAnswer;
	}


	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public List<OptionDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionDto> options) {
		this.options = options;
	}


	@Override
	public String toString() {
		return "QuestionDto [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + "]";
	}


	
}
