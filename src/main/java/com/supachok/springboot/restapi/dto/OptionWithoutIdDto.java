package com.supachok.springboot.restapi.dto;

public class OptionWithoutIdDto {
	String answer;
	public OptionWithoutIdDto() {
		super();
	}
	public OptionWithoutIdDto(String answer) {
		super();
		this.answer = answer;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "OptionWithoutIdDto [answer=" + answer + "]";
	}
	
	
}
