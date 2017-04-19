package com.voting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long voterId;
	private Long questionId;
	private Long candidateId;
	private Long ballotId;
	private Long electionId;
	
	public Vote() {
	}
	
	public Vote(Long id, Long voterId, Long questionId, Long candidateId, Long ballotId, Long electionId) {
		this.id = id;
		this.voterId = voterId;
		this.questionId = questionId;
		this.candidateId = candidateId;
		this.ballotId = ballotId;
		this.electionId = electionId;	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVoterId() {
		return voterId;
	}
	public void setVoterId(Long voterId) {
		this.voterId = voterId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public Long getBallotId() {
		return ballotId;
	}
	public void setBallotId(Long ballotId) {
		this.ballotId = ballotId;
	}
	public Long getElectionId() {
		return electionId;
	}
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}
	

}
