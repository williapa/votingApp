package com.voting.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DeletedVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long voterId;
	private Long questionId;
	private Long candidateId;
	private Long ballotId;
	private Long electionId;
	private Long rank;
	private String writein;
	private Date cast;
	private Date deleted;
	
	public DeletedVote() {
	}

	public DeletedVote(Vote v) {
		this.id = v.getId();
		this.voterId = v.getVoterId();
		this.questionId = v.getQuestionId();
		this.candidateId = v.getCandidateId();
		this.ballotId = v.getBallotId();
		this.electionId = v.getElectionId();
		this.rank = v.getRank();
		this.writein = v.getWritein();
		this.cast = v.getCast();
		this.deleted = new Date();
	}
	
	public DeletedVote(Long id, Long voterId, Long questionId, Long candidateId, Long ballotId, Long electionId, Long rank, String writein, Date cast, Date deleted) {
		this.id = id;
		this.voterId = voterId;
		this.questionId = questionId;
		this.candidateId = candidateId;
		this.ballotId = ballotId;
		this.electionId = electionId;	
		this.rank = rank;
		this.writein = writein;
		this.cast = cast;
		this.deleted = deleted;
	}
	public void setCast(Date cast) {
		this.cast = cast;
	}
	public Date getCast() {
		return cast;
	}
	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	public Date getDeleted() {
		return deleted;
	}
	public void setWritein(String writein) {
		this.writein = writein;
	}
	public String getWritein() {
		return writein;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public Long getRank() {
		return rank;
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
