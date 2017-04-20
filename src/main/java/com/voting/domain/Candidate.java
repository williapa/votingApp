package com.voting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Candidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String body;
	private Long questionId;
	private boolean writein;
	
	public Candidate() {
	}
	public Candidate(Long id, String body, Long questionId) {
		this.id = id;
		this.body = body;
		this.questionId = questionId;
		this.writein = false;
	}
	public boolean getWritein() {
		return writein;
	}
	public void setWritein(boolean writein){
		this.writein = writein;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
