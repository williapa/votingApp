package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Voter;

public interface VoterRepository extends CrudRepository<Voter, Long> {

		Voter findOne(Long id);

		List<Voter> findAll();

		Voter findByName(String name);

		List<Voter> findByRegionId(Long regionId);
}
