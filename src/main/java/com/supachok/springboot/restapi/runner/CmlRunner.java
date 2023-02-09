package com.supachok.springboot.restapi.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.supachok.springboot.restapi.entity.Option;
import com.supachok.springboot.restapi.entity.Question;
import com.supachok.springboot.restapi.entity.UserDetails;
import com.supachok.springboot.restapi.repository.SurveyRepository;
import com.supachok.springboot.restapi.repository.UserDetailsRepository;

@Component
public class CmlRunner implements CommandLineRunner {

	@Autowired
	private UserDetailsRepository repository;
	
	@Autowired
	private SurveyRepository surveyRepository;


	@Override
	public void run(String... args) throws Exception {
		repository.save(new UserDetails("test1", "admin"));
		
		long surveyId = surveyRepository.createSurvey("Servey1", "Desc of Servey 1");
		
		Question question1 = new Question("Most Popular Cloud Platform Today", "AWS");
		question1.addOptions(new Option("AWS"));
		question1.addOptions(new Option("Azure"));
		question1.addOptions(new Option("Google Cloud"));
		question1.addOptions(new Option("Oracle Cloud"));
		
		Question question2 = new Question("Fastest Growing Cloud Platform","Google Cloud");
		question2.addOptions(new Option("AWS"));
		question2.addOptions(new Option("Azure"));
		question2.addOptions(new Option("Google Cloud"));
		question2.addOptions(new Option("Oracle Cloud"));
		List<Question> questions = new ArrayList<>(Arrays.asList(question1,question2));
		
		surveyRepository.addQuestionToSurvey(surveyId, questions);
		
	}
}
