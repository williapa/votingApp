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
	
	public Candidate() {
	}
	public Candidate(Long id, String body, Long questionId) {
		this.id = id;
		this.body = body;
		this.questionId = questionId;
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
