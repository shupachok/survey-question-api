package com.supachok.springboot.restapi.dto;

import java.util.List;

public class SurveyDto {
	public SurveyDto() {

	}

	public SurveyDto(Long id, String title, String description, List<QuestionDto> questions) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.questions = questions;
	}

	private Long id;
	private String title;
	private String description;
	private List<QuestionDto> questions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Survay [id=" + id + ", title=" + title + ", description=" + description + ", questions=" + questions
				+ "]";
	}

}
