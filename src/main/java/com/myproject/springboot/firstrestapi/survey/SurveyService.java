package com.myproject.springboot.firstrestapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SurveyService {

	private static List<Survey> surveys=new ArrayList<>();
	
	static {
		Question question1 = new Question("Question1",
		        "Most Popular Cloud Platform Today", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2",
		        "Fastest Growing Cloud Platform", Arrays.asList(
		                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3",
		        "Most Popular DevOps Tool", Arrays.asList(
		                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1,
		        question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey",
		        "Description of the Survey", questions);

		surveys.add(survey);

	}

	public List<Survey> retriveAllSurveys() {
		return surveys;
	}
	
	public Survey retrieveSurveyById(String surveyId) {
		for(Survey survey:surveys) {
			if(survey.getId().equalsIgnoreCase(surveyId)) {
				return survey;
			}		
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	public List<Question> retrieveAllSurveyQuestions(String surveyId){
		Survey survey=retrieveSurveyById(surveyId);
		if(survey==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		else
			return survey.getQuestions();
	}
	
	public Question retrieveSpecificSurveyQuestion(String surveyId,String questionId){
		List<Question> questions=retrieveAllSurveyQuestions(surveyId);
		for(Question question:questions) {
			if(question.getId().equalsIgnoreCase(questionId))
				return question;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}

	public String addNewSurveyQuestion(String surveyId, Question question) {
		List<Question> questions=retrieveAllSurveyQuestions(surveyId);
		question.setId(generateRandomId());
		questions.add(question);
		return question.getId();
	}

	private String generateRandomId() {
		SecureRandom secureRandom=new SecureRandom();
		String randomId = new BigInteger(32,secureRandom).toString();
		return randomId;
	}

//	public String deleteSurveyQuestion(String surveyId, String questionId) {
//		List<Question> questions=retrieveAllSurveyQuestions(surveyId);
//		boolean questionRemoved = false;
//		Iterator<Question> iterator = questions.iterator();
//		while(iterator.hasNext()) {
//			Question question = iterator.next();
//			if(question.getId().equalsIgnoreCase(questionId))
//				iterator.remove();
//				questionRemoved=true;
//		}
//		if (questionRemoved) {
//            return questionId;
//        }
//		else {
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//		}
//		
//	}
	public String deleteSurveyQuestion(String surveyId, String questionId) {
		List<Question> questions=retrieveAllSurveyQuestions(surveyId);
		for(Question question:questions) {
			if(question.getId().equalsIgnoreCase(questionId)) {
				questions.removeIf(q->q.getId().equalsIgnoreCase(questionId));
				return questionId;
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
		List<Question> questions=retrieveAllSurveyQuestions(surveyId);
		questions.removeIf(q->q.getId().equalsIgnoreCase(questionId));
		questions.add(question);
	}
}

