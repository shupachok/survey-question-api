package com.supachok.springboot.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.supachok.springboot.restapi.entity.Question;
import com.supachok.springboot.restapi.entity.Survey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class SurveyRepository {

	@PersistenceContext
	EntityManager em;

	public long createSurvey(String title, String description) {
		Survey survey = new Survey(title, description);
		em.persist(survey);
		return survey.getId();
	}

	public List<Survey> getAll() {
		TypedQuery<Survey> queryGetAll = em.createNamedQuery("Survey.getAll", Survey.class);
		return queryGetAll.getResultList();
	}

	public Survey findById(Long surveyId) {
		return em.find(Survey.class, surveyId);
	}

	public void addQuestionToSurvey(Long surveyId, List<Question> questions) {
		Survey survey = findById(surveyId);

		questions.forEach(question -> {
			survey.addQuestions(question);
			question.setSurvey(survey);
			em.persist(question);

			question.getOptions().forEach(option -> {
				option.setQuestion(question);
				em.persist(option);
			});

		});
	}

	public Long addQuestionToSurvey(Long surveyId, Question question) {
		Survey survey = findById(surveyId);

		survey.addQuestions(question);
		question.setSurvey(survey);
		em.persist(question);

		question.getOptions().forEach(option -> {
			option.setQuestion(question);
			em.persist(option);
		});

		return question.getId();
	}
	
	public void updateQuestionToSurvey(Long surveyId, Question question) {
		Survey survey = findById(surveyId);

		question.setSurvey(survey);
		em.merge(question);

		question.getOptions().forEach(option -> {
			option.setQuestion(question);
			em.merge(option);
		});

	}

	public List<Question> getAllQuestionsBySurveyId(Long surveyId) {
		Survey survey = findById(surveyId);
		return survey.getQuestions();
	}

	public void removeQuestion(Long surveyId, Long questionId) {
		List<Question> questions = getAllQuestionsBySurveyId(surveyId);

		Optional<Question> optionQ = questions.stream().filter(q -> q.getId() == questionId).findFirst();
		if (optionQ.isPresent()) {
			em.remove(optionQ.get());
		}

		questions.removeIf(q -> q.getId() == questionId);

	}

}
