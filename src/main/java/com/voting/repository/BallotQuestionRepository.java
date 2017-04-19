package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.BallotQuestion;

public interface BallotQuestionRepository extends CrudRepository<BallotQuestion, Long>{
	
	List<BallotQuestion> findAll();
	
	List<BallotQuestion> findByBallotId(Long ballotId);

}
