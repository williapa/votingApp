package com.voting.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Voter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String password;
	private long regionId;

	public Voter(){
	}
	public Voter(String name, String password, Long regionId){
		this.name = name;
		this.password = password;;
		this.regionId = regionId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getRegionId() {
		return regionId;
	}
}