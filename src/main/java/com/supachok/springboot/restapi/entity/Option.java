package com.supachok.springboot.restapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Option {
	@Id
	@GeneratedValue
	Long id;
	 
	String answer;
	
	@ManyToOne
	Question question;

	public Option() {
	}

	public Option(String answer) {
		super();
		this.answer = answer;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", answer=" + answer + "]";
	}

}
