package com.voting.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.repository.CandidateRepository;
import com.voting.repository.QuestionRepository;
import com.voting.domain.Candidate;
import com.voting.domain.Question;

@Component
@Controller
@Path("/candidate")
@Produces(value="application/json")
@Consumes(value="application/json")
public class CandidateService {
	
	private CandidateRepository candidateRepo;
	private QuestionRepository questionRepo;
	
	@Autowired
	public void setCandidateDao(final CandidateRepository repo){
		this.candidateRepo = repo;
	}
	
	@Autowired
	public void setQuestionDao(final QuestionRepository repo){
		this.questionRepo = repo;
	}
	
	@GET
	public List<Candidate> getAll() {
		
		List<Candidate> candidates = candidateRepo.findAll();
		
		if(candidates.size() < 18) {
			saveCandidate(1L, "Reese Withoutaspoon / Cherry Garcia - Democrat");
			saveCandidate(1L, "Choco Chip Dough / Carmello Coney - Republican");
			saveCandidate(1L, "Magic Brownie / Phish Food - Independent");
			saveCandidate(2L, "Yes - retain Justice Mint C. Chip");
			saveCandidate(2L, "No - do not retain Justice Mint C. Chip");
			saveCandidate(3L, "P. Nut Butter (Republican)");
			saveCandidate(3L, "Marsh Mallow (Democrat)");
			saveCandidate(3L, "Cream C. Kol (Independent)");
			saveCandidate(4L, "Yes - for vanilla");
			saveCandidate(4L, "No - against vanilla");
			saveCandidate(5L, "Yes - for chocolate");
			saveCandidate(5L, "No - against chocolate");
			saveCandidate(6L, "Yes - for mint");
			saveCandidate(6L, "No - against mint");
			saveCandidate(7L, "Yes - Sprinkles illegal");
			saveCandidate(7L, "No - Sprinkles legal");
			saveCandidate(8L, "Yes - Coconut illegal");
			saveCandidate(8L, "No - Coconut legal");
		}
		
		return candidateRepo.findAll();
	}
	
	@POST
	@Path("/forquestion/{questionid}")
	public List<Candidate> getCandidatesForQuestion(@PathParam("questionid") String questionId) {
		Long qid = Long.parseLong(questionId);
		return candidateRepo.findByQuestionId(qid);
	}
	
	@POST
	public Candidate submitNewCandidate(Candidate candidate) {
		
		List<Candidate> candidates = candidateRepo.findByQuestionId(candidate.getQuestionId());
		
		Question q = questionRepo.findById(candidate.getQuestionId());
		
		if(q.getType().equals("yes or no") ){
			return null;
		}
		
		for(Candidate c: candidates) {
			if(c.getBody().equals(candidate.getBody())) {
				return c;
			}
		}
		
		return candidateRepo.save(candidate);
		
	}
	
	public void saveCandidate(Long questionId, String body) {
		Candidate c = new Candidate();
		c.setBody(body);
		c.setQuestionId(questionId);
		candidateRepo.save(c);
	}

}
