package com.supachok.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.supachok.springboot.restapi.controller.SurveyResource;
import com.supachok.springboot.restapi.dto.OptionDto;
import com.supachok.springboot.restapi.dto.QuestionDto;
import com.supachok.springboot.restapi.service.SurveyService;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

	@MockBean
	private SurveyService surveyService;

	@Autowired
	private MockMvc mockMvc;

	private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/1/questions/1";

	private static String GENERIC_QUESTION_URL = "http://localhost:8080/surveys/1/questions";
	
	@Test
	void retrieveQuestionBySurveyIdAndQuestionId_404Scenario() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(404, mvcResult.getResponse().getStatus());
	}
	
	@Test
	void retrieveQuestionBySurveyIdAndQuestionId_BasicScenario() throws Exception {
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
		
		List<OptionDto> options = new ArrayList<>(
										Arrays.asList(
												new OptionDto(1L, "AWS"),
												new OptionDto(2L, "Azure"),
												new OptionDto(3L, "Google Cloud"),
												new OptionDto(4L, "Oracle Cloud")
										));
		QuestionDto question = new QuestionDto(1L,"Most Popular Cloud Platform Today","AWS",options);

		when(surveyService.retrieveQuestion(1L, 1L)).thenReturn(question);
		
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();		
	
		String expectedResponse = """
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
		
		MockHttpServletResponse response = mvcResult.getResponse();
		
		assertEquals(200, response.getStatus());
		JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);
	}
	
	@Test
	void addNewSurveyQuestion_basicScenario() throws Exception {

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
		Long questionId = 1L;
		when(surveyService.createSurveyQuestion(any(Long.class),any())).thenReturn(questionId);

		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();		
		
		MockHttpServletResponse response = mvcResult.getResponse();
		String locationHeader = response.getHeader("Location");
		
		assertEquals(201, response.getStatus());
		assertTrue(locationHeader.contains("/surveys/1/questions/"+questionId));
		
	}


	

}
