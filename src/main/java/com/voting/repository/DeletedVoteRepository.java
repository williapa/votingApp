package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.DeletedVote;

public interface DeletedVoteRepository extends CrudRepository<DeletedVote, Long> {
	
	List<DeletedVote> findAll();
	
	DeletedVote findOne(Long id);

}
