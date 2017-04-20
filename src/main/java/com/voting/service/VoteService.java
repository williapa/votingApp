package com.voting.service;

import java.util.ArrayList;
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

import com.voting.repository.BallotQuestionRepository;
import com.voting.repository.CandidateRepository;
import com.voting.repository.VoteRepository;
import com.voting.domain.BallotQuestion;
import com.voting.domain.Candidate;
import com.voting.domain.Vote;

@Component
@Controller
@Path("/vote")
@Produces(value="application/json")
@Consumes(value="application/json")
public class VoteService {
	
	private VoteRepository voteRepo;
	private CandidateRepository candidateRepo;
	private BallotQuestionRepository ballotQuestionRepo;

	@Autowired
	public void setVoteDao(final VoteRepository repo){
		this.voteRepo = repo;
	}
	
	@Autowired
	public void setCandidateDao(final CandidateRepository repo){
		this.candidateRepo = repo;
	}
	
	@Autowired
	public void setBallotQuestionDao(final BallotQuestionRepository repo){
		this.ballotQuestionRepo = repo;
	}
	
	@GET
	public List<Vote> getAll() {
		
		return voteRepo.findAll();
		
	}
	
	@GET
	@Path("/question/{questionid}")
	public List<Vote> getVotesForQuestion(@PathParam("questionid") Long questionId) {
		
		return voteRepo.findByQuestionId(questionId);
		
	}
	
	@GET
	@Path("/ballot/{ballotid}")
	public List<Vote> getVotesForBallot(@PathParam("ballotid") Long ballotId) {
		List<BallotQuestion> ballotQuestions = ballotQuestionRepo.findByBallotId(ballotId); 
		List<Vote> allVotes = new ArrayList<Vote>();
		
		for(BallotQuestion bq: ballotQuestions) {
			Long questionId = bq.getQuestionId();
			List<Vote> votes = voteRepo.findByQuestionId(questionId);
			allVotes.addAll(votes);
		}
		
		return allVotes;
	}
	
	@GET
	@Path("/voter/{voterId}")
	public List<Vote> getVotesForVoter(@PathParam("voteId") Long voterId) {
		
		return voteRepo.findByVoterId(voterId);
		
	}
	
	@POST
	public List<Vote> submitVote(List<Vote> vote) {
		
		List<Vote> successfulVotes = new ArrayList<>();

		System.out.println("hello...");

		System.out.println("votes size: ");
		System.out.println(vote.size());
		
		//depending on type, save candidate and create / save votes
		for(Vote v: vote) {

			System.out.println("here is a vote: ");
			System.out.println("voter id: "+v.getVoterId());
			System.out.println("question id: "+v.getQuestionId());
			System.out.println("candidate id: "+v.getCandidateId());
			System.out.println("election id: "+v.getElectionId());
			System.out.println("write in : "+v.getWritein());
			System.out.println("rank: "+v.getRank());
			
			Long questionId = v.getQuestionId();
			List<Vote> existingVotes = voteRepo.findByVoterId(v.getVoterId());
			List<Vote> oldVotes = new ArrayList<>();
			
			for(Vote e: existingVotes) {
				
				Long existingQuestionId = e.getQuestionId();
				
				if(questionId == existingQuestionId) {
					
					oldVotes.add(e);
					
				}
				
			}
			
			if(oldVotes.size() > 0) {
				
				for(Vote old: oldVotes) {
					
					voteRepo.delete(old);
					
				}
				
			}

			if(v.getCandidateId() == -1L) {

				List<Candidate> candidatesWithSameName = candidateRepo.findByBody(v.getWritein());

				Long existingCandidateId = -1L;

				for(Candidate c: candidatesWithSameName) {

					if(c.getQuestionId() == v.getQuestionId()) {
						existingCandidateId = c.getId();
					}

				}
				if(existingCandidateId > -1L) {

					v.setCandidateId(existingCandidateId);

				} else {

					Candidate cand = new Candidate();

					cand.setBody(v.getWritein());

					cand.setQuestionId(v.getQuestionId());

					Candidate saved = candidateRepo.save(cand);

					v.setCandidateId(saved.getId());
				}

			} 

			Candidate c = candidateRepo.findOne(v.getCandidateId());
			
			if(c != null) {

				voteRepo.save(v);
			
				successfulVotes.add(v);
					
			}
			
		}
		
		return successfulVotes;
	
	}

}
