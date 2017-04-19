package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Candidate;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
	
	Candidate findOne(Long id);
	
	List<Candidate> findAll();
	
	List<Candidate> findByQuestionId(Long id);

}
