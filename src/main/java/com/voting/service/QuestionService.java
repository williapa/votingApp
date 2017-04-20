package com.voting.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.repository.BallotRepository;
import com.voting.repository.QuestionRepository;

import com.voting.domain.Question;

@Component
@Controller
@Path("/question")
@Produces(value="application/json")
@Consumes(value="application/json")
public class QuestionService {
	
	private QuestionRepository questionRepo;
	
	@Autowired
	public void setQuestionDao(final QuestionRepository repo) {
		this.questionRepo = repo;
	}
	
	@GET
	public List<Question> getAll() {
		
		return questionRepo.findAll();
		
	}
	
	@POST
	public Question submitQuestion(Question q) {
		Question exists = questionRepo.findByBody(q.getBody());
		
		if(exists != null && (q.getType().equals("instant runoff") || q.getType().equals("yes or no") || q.getType().equals("pick two"))) {
			return questionRepo.save(q);
		}

		return null;
	}
	
	@GET
	@Path("/{questionId}")
	public Question getQuestionById(@PathParam("questionId") Long questionId) {
		
		return questionRepo.findOne(questionId);
		
	}
	
	public void saveQuestion(String body, String type) {
		
		Question exists = questionRepo.findByBody(body);
		
		if(exists == null) {
			Question quest = new Question();
			quest.setBody(body);
			quest.setType(type);
			questionRepo.save(quest);
		}

	}
	
	@PostConstruct
	public void saveQuestions() {
		saveQuestion("Commnder in Cream", "instant runoff");
		saveQuestion("Chief Dairy Queen", "yes or no");
		saveQuestion("State Rep. District M&M", "pick two");
		saveQuestion("Make Vanilla best flavor", "yes or no");
		saveQuestion("Make Chocolate best flavor", "yes or no");
		saveQuestion("Make Mint the Best Flavor", "yes or no");
		saveQuestion("Make Sprinkles an Illegal Flavor", "yes or no");
		saveQuestion("Make coconut an illegal topping", "yes or no");
		System.out.println("questions saved.");
	}
	
	
}
