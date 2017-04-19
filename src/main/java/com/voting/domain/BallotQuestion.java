package com.voting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BallotQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long questionId;
	private Long ballotId;
	
	public BallotQuestion() {
	}
	public BallotQuestion(Long id, Long questionId, Long ballotId) {
		this.id = id;
		this.questionId = questionId;
		this.ballotId = ballotId;		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getBallotId() {
		return ballotId;
	}
	public void setBallotId(Long ballotId) {
		this.ballotId = ballotId;
	}

}
