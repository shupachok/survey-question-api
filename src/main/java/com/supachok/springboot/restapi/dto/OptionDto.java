package com.supachok.springboot.restapi.dto;

public class OptionDto {
	Long id;
	String answer;
	public OptionDto() {
		super();
	}
	public OptionDto(Long id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "OptionDto [id=" + id + ", answer=" + answer + "]";
	}
	
}
