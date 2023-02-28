package com.supachok.springboot.restapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supachok.springboot.restapi.config.exception.QuestionNotFoundException;
import com.supachok.springboot.restapi.config.exception.SurveyNotFoundException;
import com.supachok.springboot.restapi.dto.QuestionDto;
import com.supachok.springboot.restapi.dto.QuestionWithoutIdDto;
import com.supachok.springboot.restapi.dto.SurveyDto;
import com.supachok.springboot.restapi.service.SurveyService;
@RestController
public class SurveyResource {

	@Autowired
	MessageSource messageSource;
	
	SurveyService surveyService;
	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
		
	}

	@GetMapping("/surveys")
	public List<SurveyDto> retrieveAllSurveys() {
		List<SurveyDto> serveys = surveyService.retrieveAllSurveys();
		return serveys;
	}

	@GetMapping("/surveys/{surveyId}")
	public SurveyDto retrieveSurveysById(@PathVariable Long surveyId) {
		SurveyDto survey = surveyService.retrieveSurveyById(surveyId);
		if (survey == null)
			throw new SurveyNotFoundException(surveyId);
		return survey;
	}

	@GetMapping("/surveys/{surveyId}/questions")
	public List<QuestionDto> retrieveAllQuestionsBySurveyId(@PathVariable Long surveyId) {
		List<QuestionDto> questions = surveyService.retrieveQuestionsBySurveyId(surveyId);
		if (questions == null)
			throw new SurveyNotFoundException(surveyId);
		return questions;
	}

	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public QuestionDto retrieveQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId) {
		QuestionDto question = surveyService.retrieveQuestion(surveyId, questionId);
		if (question == null)
			throw new QuestionNotFoundException(questionId);
		return question;
	}

	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> createSurveyQuestion(@PathVariable long surveyId, @RequestBody QuestionWithoutIdDto question) {
		
		Long questionId = surveyService.createSurveyQuestion(surveyId, question);

		if(questionId == null)
			return ResponseEntity.notFound().build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId)
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId) {
		Long questionIdDeleted = surveyService.deleteQuestion(surveyId, questionId);
		if (questionIdDeleted == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> updateQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId,@RequestBody QuestionDto question) {
		Long questionIdUpdated = surveyService.updateQuestion(surveyId, questionId,question);
		if (questionIdUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}

}
