package com.supachok.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {
	private static String SPECIFIC_QUESTION_URL = "/surveys/1/questions/1";
	private static String QUESTIONS_URL = "/surveys/1/questions";

	@Autowired
	private TestRestTemplate template;

	@Test
	void retrieveSpecificSurveyQuestion_basicScenario() throws JSONException {
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> responseEntity 
			= template.exchange(SPECIFIC_QUESTION_URL, HttpMethod.GET, httpEntity, String.class);
		
		String expectedResult = """
				{
					"id": 1,
					"description": "Most Popular Cloud Platform Today",
					"correctAnswer": "AWS",
					"options": [
						{
							"id": 1,
							"answer": "AWS"
						},
						{
							"id": 2,
							"answer": "Azure"
						},
						{
							"id": 3,
							"answer": "Google Cloud"
						},
						{
							"id": 4,
							"answer": "Oracle Cloud"
						}
					]
				}
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		JSONAssert.assertEquals(expectedResult.trim(), responseEntity.getBody(), true);
	}
	
	@Test
	void retrieveAllQuestionsBySurveyId_basicScenario() throws JSONException {
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> responseEntity 
			= template.exchange(QUESTIONS_URL, HttpMethod.GET, httpEntity, String.class);
		
		String expectedResult = """
				[
				    {
				        "id": 1
				    },
				    {
				        "id": 2
				    }
				]
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		JSONAssert.assertEquals(expectedResult, responseEntity.getBody(), false);
		
	}
	
	@Test
	@DirtiesContext
	void addNewSurveyQuestion_basicScenario() {
		String requestBody = """
					{
					"description": "Most Favorite Language",
					"correctAnswer": "Java",
					"options": [
						{
						"answer": "Java"
						},
						{
						"answer": "Python"
						},
						{
						"answer": "JavaScript"
						},
						{
						"answer": "Assembly"
						}
					]
					}
				""";
		
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
		
		ResponseEntity<String> responseEntity 
			= template.exchange(QUESTIONS_URL, HttpMethod.POST, httpEntity, String.class);
		
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/1/questions/"));
		
	}
	
	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		return headers;
	}
	
	
	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		return new String(encodedBytes);
	}
}
