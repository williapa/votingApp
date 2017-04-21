package com.voting.domain;

public class VoteResult {
	
	private String question;
	private Long questionId; 
	private String candidate;
	private Long ballotId;
	private Long voterId;
	private Long id;
	private Long rank;
	
	public VoteResult() {
	}
	public VoteResult(Long id, String question, Long questionId, String candidate, Long ballotId, Long rank) {
		this.id = id;
		this.question = question;
		this.questionId = questionId;
		this.candidate = candidate;
		this.ballotId = ballotId;
		this.rank = rank;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setBallotId(Long ballotId) {
		this.ballotId = ballotId;
	}
	public Long getBallotId() {
		return ballotId;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion() {
		return question;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public String getCandidate() {
		return candidate;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}

}
