package com.supachok.springboot.restapi.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supachok.springboot.restapi.dto.QuestionDto;
import com.supachok.springboot.restapi.dto.QuestionWithoutIdDto;
import com.supachok.springboot.restapi.dto.SurveyDto;
import com.supachok.springboot.restapi.entity.Question;
import com.supachok.springboot.restapi.entity.Survey;
import com.supachok.springboot.restapi.repository.QuestionRepository;
import com.supachok.springboot.restapi.repository.SurveyRepository;

@Service
public class SurveyService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	SurveyRepository surveyRepository;

	@Autowired
	QuestionRepository questionRepository;

	public List<SurveyDto> retrieveAllSurveys() {
		List<Survey> surveys = surveyRepository.getAll();
		return surveys.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public SurveyDto retrieveSurveyById(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId);
		if (survey == null)
			return null;
		return convertToDto(survey);
	}

	public List<QuestionDto> retrieveQuestionsBySurveyId(Long surveyId) {
		SurveyDto survey = retrieveSurveyById(surveyId);
		if (survey == null)
			return null;
		return survey.getQuestions();
	}

	public QuestionDto retrieveQuestion(Long surveyId, Long questionId) {
		List<QuestionDto> questions = retrieveQuestionsBySurveyId(surveyId);
		if (questions == null)
			return null;
		Optional<QuestionDto> optionalQuestion = questions.stream().filter(q -> q.getId().equals(questionId))
				.findFirst();
		if (optionalQuestion.isEmpty())
			return null;
		return optionalQuestion.get();
	}

	public Long createSurveyQuestion(long surveyId, QuestionWithoutIdDto question) {
		SurveyDto survey = retrieveSurveyById(surveyId);
		if (survey == null)
			return null;
		
		Question questionEntity = convertToEntity(question);
		Long questionId = surveyRepository.addQuestionToSurvey(surveyId, questionEntity);

		return questionId;
	}

	public Long deleteQuestion(Long surveyId, Long questionId) {
		QuestionDto question = retrieveQuestion(surveyId, questionId);
		if (question == null)
			return null;
		questionRepository.deleteById(questionId);

		return questionId;
	}

	public Long updateQuestion(Long surveyId, Long questionId, QuestionDto question) {
		SurveyDto survey = retrieveSurveyById(surveyId);
		if (survey == null)
			return null;

		Question questionEntity = convertToEntity(question);
		surveyRepository.updateQuestionToSurvey(surveyId, questionEntity);
		
		return questionId;
	}

	private SurveyDto convertToDto(Survey survey) {
		SurveyDto surveyDto = modelMapper.map(survey, SurveyDto.class);
		return surveyDto;
	}

	private String generateRandomId() {
		SecureRandom securRandom = new SecureRandom();
		String randomId = new BigInteger(32, securRandom).toString();
		return randomId;
	}

	private Survey convertToEntity(SurveyDto surveyDto) {
		Survey survey = modelMapper.map(surveyDto, Survey.class);
		return survey;
	}

	private Question convertToEntity(QuestionDto questionDto) {
		Question question = modelMapper.map(questionDto, Question.class);
		return question;
	}
	
	private Question convertToEntity(QuestionWithoutIdDto questionDto) {
		Question question = modelMapper.map(questionDto, Question.class);
		return question;
	}
	
	
}
