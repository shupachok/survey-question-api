package com.supachok.springboot.restapi.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Question {

	@Id
	@GeneratedValue
	Long id;
	
	String description;

	@OneToMany(mappedBy = "question")
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<Option> options = new ArrayList<>();
	
	@ManyToOne
	Survey survey;
	
	String correctAnswer;

	public Question() {

	}

	public Question(String description, String correctAnswer) {
		super();
		this.description = description;
		this.correctAnswer = correctAnswer;
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public void addOptions(Option option) {
		this.options.add(option);
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + "]";
	}
}
