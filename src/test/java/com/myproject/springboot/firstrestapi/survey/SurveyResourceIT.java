package com.myproject.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

	private static String SPECIFIC_QUESTION_URL="/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTION_URL="/surveys/Survey1/questions";
	
	@Autowired
	private TestRestTemplate template;
	
	@Test
	void retrieveSpecificSurveyQuestionTest() throws JSONException {
		ResponseEntity<String> responseEntity=template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
		
		String expectedResponse=
				"""
					{
						"id": "Question1",
						"description": "Most Popular Cloud Platform Today",
						"options": [
						        "AWS",
						        "Azure",
						        "Google Cloud",
						        "Oracle Cloud"
						    ],
						"correctAnswer": "AWS"
					}		
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}

	@Test
	void retrieveAllSurveyQuestionTest() throws JSONException {
		ResponseEntity<String> responseEntity=template.getForEntity(GENERIC_QUESTION_URL, String.class);
		
		String expectedResponse=
				"""
					[
					    {
					        "id": "Question1",
					        "description": "Most Popular Cloud Platform Today",
					        "options": [
					            "AWS",
					            "Azure",
					            "Google Cloud",
					            "Oracle Cloud"
					        ],
					        "correctAnswer": "AWS"
					    },
					    {
					        "id": "Question2",
					        "description": "Fastest Growing Cloud Platform",
					        "options": [
					            "AWS",
					            "Azure",
					            "Google Cloud",
					            "Oracle Cloud"
					        ],
					        "correctAnswer": "Google Cloud"
					    },
					    {
					        "id": "Question3",
					        "description": "Most Popular DevOps Tool",
					        "options": [
					            "Kubernetes",
					            "Docker",
					            "Terraform",
					            "Azure DevOps"
					        ],
					        "correctAnswer": "Kubernetes"
					    }
					]		
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}
	
	@Test
	void retrieveAllSurveyQuestionTest2() throws JSONException {
		ResponseEntity<String> responseEntity=template.getForEntity(GENERIC_QUESTION_URL, String.class);
		
		String expectedResponse=
				"""
					[
					    {
					        "id": "Question1"
					    },
					    {
					        "id": "Question2"
					    },
					    {
					        "id": "Question3"
					    }
					]		
				""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
	}
	
	@Test
	void addNewSurveyQuestionTest() {
		String requestBody=
				"""
					{
					    "description": "Your Favorite Programming Language",
					    "options": [
					        "Java",
					        "C",
					        "C++",
					        "Python"
					    ],
					    "correctAnswer": "Java"
					}
				""";
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> httpEntity=new HttpEntity<String>(requestBody,headers);
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTION_URL,HttpMethod.POST,httpEntity,String.class);
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
		template.delete(locationHeader);
	}
}

