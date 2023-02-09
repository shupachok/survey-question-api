package com.supachok.springboot.restapi.repository;

import org.springframework.stereotype.Repository;

import com.supachok.springboot.restapi.entity.Question;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class QuestionRepository {

	@PersistenceContext
	EntityManager em;

	public Question findById(Long questionId) {
		return em.find(Question.class, questionId);
	}
	
	public void deleteById(Long questionId) {
		Question question = findById(questionId);
		em.remove(question);
	}
}
