package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {
	
	List<Vote> findAll();
	
	List<Vote> findByVoterId(Long voterId);
	
	List<Vote> findByQuestionId(Long questionId);

	List<Vote> findByCandidateId(Long candidateId);
	
	Vote findOne(Long id);

}
