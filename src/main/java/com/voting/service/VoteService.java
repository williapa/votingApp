package com.voting.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

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
import com.voting.repository.BallotRepository;
import com.voting.repository.QuestionRepository;
import com.voting.repository.CandidateRepository;
import com.voting.repository.VoteRepository;
import com.voting.repository.VoterRepository;
import com.voting.repository.DeletedVoteRepository;
import com.voting.repository.ElectionRepository;

import com.voting.domain.*;

import com.voting.security.*;

@Component
@Controller
@Path("/vote")
@Produces(value="application/json")
@Consumes(value="application/json")
public class VoteService {
	
	private VoterRepository voterRepo;
	private VoteRepository voteRepo;
	private CandidateRepository candidateRepo;
	private BallotQuestionRepository ballotQuestionRepo;
	private BallotRepository ballotRepo;
	private QuestionRepository questionRepo;
	private DeletedVoteRepository deletedVoteRepo;
	private ElectionRepository electionRepo;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private WebTokenEncryption crypt;

	@Autowired
	public void setElectionDao(final VoterRepository repo) {
		this.voterRepo = repo;
	}

	@Autowired
	public void setElectionDao(final ElectionRepository repo) {
		this.electionRepo = repo;
	}

	@Autowired
	public void setDeletedVoteDao(final DeletedVoteRepository repo) {
		this.deletedVoteRepo = repo;
	}

	@Autowired
	public void setQuestionDao(final QuestionRepository repo) {
		this.questionRepo = repo;
	}
	@Autowired
	public void setBallotDao(final BallotRepository repo) {
		this.ballotRepo = repo;
	}
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
	@Path("/election/{electionid}") 
	public List<VoteResult> getVotesForElection(@PathParam("electionid") Long electionId) {

		List<Vote> votes = voteRepo.findAll();

		//now for allVotes, we need to assign the question body and candidate name. 
		List<VoteResult> voteResults = new ArrayList<>();

		System.out.println("this many vote results: ");
		System.out.println(votes.size());

		for(Vote v: votes) {

			VoteResult vr = new VoteResult();

			String candidate = candidateRepo.findOne(v.getCandidateId()).getBody();
			String question = questionRepo.findOne(v.getQuestionId()).getBody();

			vr.setQuestion(question);
			vr.setCandidate(candidate);
			vr.setBallotId(v.getBallotId());
			vr.setId(v.getId());
			vr.setQuestionId(v.getQuestionId());
			vr.setRank(v.getRank());

			voteResults.add(vr);
		}

		System.out.println("vote results size: ");
		System.out.println(voteResults.size());
		
		return voteResults;

	}

	@GET
	@Path("/question/winner/{questionid}")
	public List<Candidate> getWinnerForQuestion(@PathParam("questionid") String questionid) {

		System.out.println("hello get winner for question: "+ questionid);

		Long questionId = Long.parseLong(questionid);

		System.out.println("long question id: "+ questionId);
		
		List<Vote> questionVotes = voteRepo.findByQuestionId(questionId);
		Question q = questionRepo.findOne(questionId);
		List<Candidate> winningCandidates = new ArrayList<>();

		System.out.println("question: "+ q.getBody());
		System.out.println("question type: "+q.getType());
		System.out.println("length: "+ questionVotes.size());

		if(questionVotes.isEmpty()) {
			return null;
		}

		if(q.getType().equals("yes or no") || q.getType().equals("pick two")) {

			Map<Long, Long> votes = new HashMap<Long, Long>();

			for(Vote v : questionVotes) {

				if(votes.containsKey(v.getCandidateId())) {
					Long val = votes.get(v.getCandidateId());
					val = val + 1L;
					votes.put(v.getCandidateId(), val);
				} else {

					votes.put(v.getCandidateId(), 1L);

				}

			}

			for (Long key : votes.keySet()) {
				Candidate kand = candidateRepo.findOne(key);
				kand.setId(votes.get(key));
				winningCandidates.add(kand);
			}

			return winningCandidates;

		} else if (q.getType().equals("instant runoff")) {

			Set<Long> candydates = new TreeSet<Long>();
			Set<Long> voters = new TreeSet<Long>();

			for(Vote v : questionVotes) {

				candydates.add(v.getCandidateId());	
				voters.add(v.getVoterId());	

			}
			List<List<Vote>> runoffBallots = new ArrayList<>();

			for(Long v : voters) {

				List<Vote> rankedVotes = new ArrayList<Vote>();

				for(Vote x: questionVotes) {

					if(x.getVoterId() == v) {

						int rank = x.getRank().intValue() - 1;

						rankedVotes.add(rank, x);

					}

				}

				runoffBallots.add(rankedVotes);

			}

			while(candydates.size() > 1) {
				//  <candidateId, # of votes>
				Map<Long, Integer> voteTally = countVotes(candydates, runoffBallots);
				Long loser = pickRandomItem(getLosers(candydates, voteTally));
				candydates.remove(loser);
			}

			if(candydates.size() > 0) {
				winningCandidates.add(candidateRepo.findOne(candydates.iterator().next()));
			} 	

		}

		return winningCandidates;

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

		System.out.println("votes size: ");
		System.out.println(vote.size());

		//CHECK REQUEST HEADER, RETURN NULL IF AUTH TOKEN DOESNT MATCH VOTER ID
		Long vwt = checkHeaderForToken();
		if(vote.isEmpty() || vwt != vote.get(0).getVoterId()) {
			System.out.println("VOTER FRAUD!!!");
			//TODO: create voter fraud table
			return null;
		}

		//RETURN NULL IF WE ARE OUTSIDE OF THE ELECTION WINDOW
		if(vote.size() > 0) {
			Vote v = vote.get(0);
			Election e = electionRepo.findOne(v.getElectionId());

			Date currentTime = new Date();
			Date electionStart = e.getStartDate();
			Date electionEnd = e.getEndDate();

			if(currentTime.compareTo(electionStart) < 0 || currentTime.compareTo(electionEnd) > 0) {
				System.out.println("election is over or has not started.");
				return successfulVotes;
			}

		}

		//DELETE OLD VOTES
		List<Vote> existingVotes = voteRepo.findByVoterId(vote.get(0).getVoterId());
		List<Vote> oldVotes = new ArrayList<>();

		for(Vote e : existingVotes) {
				
			oldVotes.add(e);
				
		}
			
		if(oldVotes.size() > 0) {
				
			for(Vote old : oldVotes) {
					
				voteRepo.delete(old);

				DeletedVote dv = new DeletedVote(old);

				deletedVoteRepo.save(dv);
					
			}
				
		}
		
		//depending on the vote's question type, save candidate and create / save votes
		for(Vote v: vote) {
			
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

					cand.setWritein(true);

					Candidate saved = candidateRepo.save(cand);

					v.setCandidateId(saved.getId());
				}

			} 

			Candidate c = candidateRepo.findOne(v.getCandidateId());
			
			if(c != null) {

				System.out.println("candidate name: " + c.getBody());

				v.setCast(new Date());

				voteRepo.save(v);
			
				successfulVotes.add(v);
					
			}
			
		}
		
		return successfulVotes;
	
	}

	private static ArrayList<Long> getLosers(Set<Long> candidates, Map<Long, Integer> voteTally) {

		ArrayList<Long> loserlist = new ArrayList<>();
		int minTally = Integer.MAX_VALUE;

		for(Long candidate : voteTally.keySet()){ 

			int candidateTally = voteTally.get(candidate);

			if(candidateTally < minTally) {
				loserlist.clear();
				loserlist.add(candidate);
				minTally = candidateTally;
			} else if (candidateTally == minTally) {
				loserlist.add(candidate);
			}

		}
		return loserlist;

	}

	private static Long pickRandomItem(ArrayList<Long> list) {

		return list.get((int) (Math.random()*list.size()));

	}

	private static Map<Long, Integer> countVotes(Set<Long> candidates, List<List<Vote>> ballots) {

		Map<Long, Integer> voteTally = new TreeMap<Long, Integer>();

		for(Long c : candidates) {

			voteTally.put(c, 0);

		} 

		for(List<Vote> v : ballots) {

			Long topChoice = getTopChoice(v, candidates);
			if(topChoice != null) {
				int currentTally = voteTally.get(topChoice);
				voteTally.put(topChoice, currentTally + 1);
			}

		}

		return voteTally;

	}

	private static Long getTopChoice(List<Vote> ballot, Set<Long> candidates) {

		Iterator<Vote> iter = ballot.iterator();

		while(iter.hasNext()) {
			Vote c = iter.next();
			if(candidates.contains(c.getCandidateId())) {
				return c.getCandidateId();
			}
		}
		return null;

	}

	private Long checkHeaderForToken() {

		String voterToken = request.getHeader("Auth-Token");

		System.out.println("voter token: " + voterToken);

		if(voterToken != null) {

			String voterId = crypt.decrypt(voterToken);

			System.out.println("voter id: "+voterId);

			Long id = Long.parseLong(voterId);

			return id;
		}

		return -1L;

	}

}
