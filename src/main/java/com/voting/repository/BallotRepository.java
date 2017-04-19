package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Ballot;

public interface BallotRepository extends CrudRepository<Ballot, Long> {
	
	List<Ballot> findAll();
	
	List<Ballot> findByRegionId(Long regionId);
	
	List<Ballot> findByElectionId(Long electionId);
	
	Ballot findOne(Long id);

}
