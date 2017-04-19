package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Election;

public interface ElectionRepository extends CrudRepository<Election, Long> {

		Election findOne(Long id);

		List<Election> findAll();

}
