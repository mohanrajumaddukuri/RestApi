package com.myproject.springboot.firstrestapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {

	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/surveys")
	public List<Survey> retriveAllSurveys(){
		return surveyService.retriveAllSurveys();
	}
	
	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		return surveyService.retrieveSurveyById(surveyId);
	}
	
	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
		return surveyService.retrieveAllSurveyQuestions(surveyId);
	}
	
	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId) {
		return surveyService.retrieveSpecificSurveyQuestion(surveyId,questionId);
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions",method=RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId,@RequestBody Question question) {
		String questionId=surveyService.addNewSurveyQuestion(surveyId,question);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId) {
		surveyService.deleteSurveyQuestion(surveyId,questionId);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions/{questionId}",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId,
			@PathVariable String questionId,@RequestBody Question question) {
		surveyService.updateSurveyQuestion(surveyId,questionId,question);
		return ResponseEntity.noContent().build();
	}
}
