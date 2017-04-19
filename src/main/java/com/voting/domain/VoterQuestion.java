package com.voting.domain;

import java.util.List;

public class VoterQuestion {
	
	private Question q;
	private List<Candidate> candidates;
	
	public VoterQuestion(){
	}

	public VoterQuestion(Question q, List<Candidate> candidates) {
		this.q = q;
		this.candidates = candidates;
	}

	public Question getQ() {
		return q;
	}

	public void setQ(Question q) {
		this.q = q;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

}
