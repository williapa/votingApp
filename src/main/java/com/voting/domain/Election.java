package com.voting.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Election {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date startDate;
	private Date endDate;

	public Election () {
	}

	public Election  (long id, Date startDate, Date endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}