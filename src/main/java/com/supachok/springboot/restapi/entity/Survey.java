package com.supachok.springboot.restapi.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQuery(name = "Survey.getAll",query = "select s from Survey s")
public class Survey {

	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String description;
	
	@OneToMany(mappedBy = "survey")
	private List<Question> questions = new ArrayList<>();

	public Survey() {
	}

	public Survey(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestions(Question question) {
		this.questions.add(question);
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
	
	

}
