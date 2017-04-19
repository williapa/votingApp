package com.voting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ballot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long regionId;
	private Long electionId;
	
	public Ballot() {
	}
	public Ballot(Long id, Long regionId, Long electionId) {
		this.id = id;
		this.regionId = regionId;
		this.electionId = electionId;	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getElectionId() {
		return electionId;
	}
	public void setElectionId(Long electionId) {
		this.electionId = electionId;
	}
}
