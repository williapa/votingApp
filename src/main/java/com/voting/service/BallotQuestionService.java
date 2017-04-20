package com.voting.service;

import java.util.ArrayList;
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

import com.voting.domain.BallotQuestion;
import com.voting.domain.Question;
import com.voting.domain.VoterQuestion;
import com.voting.domain.Candidate;
import com.voting.repository.BallotQuestionRepository;
import com.voting.repository.CandidateRepository;
import com.voting.repository.QuestionRepository;

@Component
@Controller
@Path("/ballotquestion")
@Produces(value="application/json")
@Consumes(value="application/json")
public class BallotQuestionService {
	
	private BallotQuestionRepository ballotQuestionRepo;
	private QuestionRepository questionRepo;
	private CandidateRepository candidateRepo;
	
	@Autowired
	public void setQuestionDao(final QuestionRepository repo) {
		this.questionRepo = repo;
	}
	@Autowired
	public void setBallotQuestionDao(final BallotQuestionRepository repo) {
		this.ballotQuestionRepo = repo;
	}
	@Autowired
	public void setCandidateDao(final CandidateRepository repo) {
		this.candidateRepo = repo;
	}
	
	@GET
	public List<BallotQuestion> getAll() {
		return ballotQuestionRepo.findAll();
	}
	
	@GET
	@Path("/forballot/{ballotid}")
	public List<VoterQuestion> getForBallotId(@PathParam("ballotid") String ballotId) {
		
		Long bId = Long.parseLong(ballotId);
		
		List<BallotQuestion> questionIds = ballotQuestionRepo.findByBallotId(bId);
		
		List<VoterQuestion> questions = new ArrayList<>();
	
		for(BallotQuestion id: questionIds) {
			Question nextQuestion = questionRepo.findById(id.getQuestionId());
			VoterQuestion vq = new VoterQuestion();
			vq.setQ(nextQuestion);
			List<Candidate> cs = candidateRepo.findByQuestionId(id.getQuestionId());
			List<Candidate> noWriteins =  new ArrayList<Candidate>();
			for(int i = 0; i < cs.size(); i++) {
				if(!cs.get(i).getWritein()) {
					noWriteins.add(cs.get(i));
				}
			}
			vq.setCandidates(noWriteins);

			questions.add(vq);
		}
		return questions;
		
	}
	
	@POST
	public BallotQuestion addQuestionToBallot(BallotQuestion ballotQuestion) {
		
		List<BallotQuestion> questions = ballotQuestionRepo.findByBallotId(ballotQuestion.getBallotId());
		
		boolean save = true;
		
		for(BallotQuestion bq: questions) {
			
			if(bq.getQuestionId() == ballotQuestion.getQuestionId()){
				save = false;
			}
			
		}
		if(save) {
			return ballotQuestionRepo.save(ballotQuestion);
		}
		return null;
	}
	
	@PostConstruct
	public void saveBallotQuestions() {
		saveBallotQuestion(1L, 1L);
		saveBallotQuestion(1L, 2L);
		saveBallotQuestion(1L, 3L);
		saveBallotQuestion(1L, 4L);
		saveBallotQuestion(2L, 1L);
		saveBallotQuestion(2L, 2L);
		saveBallotQuestion(2L, 3L);
		saveBallotQuestion(2L, 5L);
		saveBallotQuestion(3L, 1L);
		saveBallotQuestion(3L, 2L);
		saveBallotQuestion(3L, 3L);
		saveBallotQuestion(3L, 6L);
		saveBallotQuestion(4L, 1L);
		saveBallotQuestion(4L, 2L);
		saveBallotQuestion(4L, 3L);
		saveBallotQuestion(4L, 7L);
		saveBallotQuestion(5L, 1L);
		saveBallotQuestion(5L, 2L);
		saveBallotQuestion(5L, 3L);
		saveBallotQuestion(5L, 8L);
		System.out.println("ballot questions saved.");
	}
	
	public void saveBallotQuestion(Long ballotId, Long questionId) {
		BallotQuestion bq = new BallotQuestion();
		bq.setBallotId(ballotId);
		bq.setQuestionId(questionId);
		ballotQuestionRepo.save(bq);		
	}
	

}
