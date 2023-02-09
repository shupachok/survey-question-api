package com.supachok.springboot.restapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supachok.springboot.restapi.dto.QuestionDto;
import com.supachok.springboot.restapi.dto.QuestionWithoutIdDto;
import com.supachok.springboot.restapi.dto.SurveyDto;
import com.supachok.springboot.restapi.service.SurveyService;

@RestController
public class SurveyResource {

	SurveyService surveyService;
	
	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	@RequestMapping("/surveys")
	public List<SurveyDto> retrieveAllSurveys() {
		List<SurveyDto> serveys = surveyService.retrieveAllSurveys();
		return serveys;
	}

	@RequestMapping("/surveys/{surveyId}")
	public SurveyDto retrieveSurveysById(@PathVariable Long surveyId) {
		SurveyDto survey = surveyService.retrieveSurveyById(surveyId);
		if (survey == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey;
	}

	@RequestMapping("/surveys/{surveyId}/questions")
	public List<QuestionDto> retrieveAllQuestionsBySurveyId(@PathVariable Long surveyId) {
		List<QuestionDto> questions = surveyService.retrieveQuestionsBySurveyId(surveyId);
		if (questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return questions;
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public QuestionDto retrieveQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId) {
		QuestionDto question = surveyService.retrieveQuestion(surveyId, questionId);
		if (question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return question;
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> createSurveyQuestion(@PathVariable long surveyId, @RequestBody QuestionWithoutIdDto question) {
		
		Long questionId = surveyService.createSurveyQuestion(surveyId, question);

		if(questionId == null)
			return ResponseEntity.notFound().build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId)
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId) {
		Long questionIdDeleted = surveyService.deleteQuestion(surveyId, questionId);
		if (questionIdDeleted == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateQuestionBySurveyIdAndQuestionId(@PathVariable Long surveyId,
			@PathVariable Long questionId,@RequestBody QuestionDto question) {
		Long questionIdUpdated = surveyService.updateQuestion(surveyId, questionId,question);
		if (questionIdUpdated == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.noContent().build();
	}

}
