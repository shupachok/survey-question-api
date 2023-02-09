package com.supachok.springboot.restapi.dto;

import java.util.List;

public class QuestionWithoutIdDto {
	
	String description;
	
	String correctAnswer;
	List<OptionWithoutIdDto> options;
	public QuestionWithoutIdDto() {
	}

	
	public QuestionWithoutIdDto(String description, String correctAnswer,List<OptionWithoutIdDto> options) {
		super();
		this.description = description;
		this.correctAnswer = correctAnswer;
		this.options = options;
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

	public List<OptionWithoutIdDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionWithoutIdDto> options) {
		this.options = options;
	}


	@Override
	public String toString() {
		return "QuestionWithoutIdDto [description=" + description + ", correctAnswer=" + correctAnswer + ", options="
				+ options + "]";
	}
	
}
