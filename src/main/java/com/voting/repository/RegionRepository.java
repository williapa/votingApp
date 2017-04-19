package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Region;

public interface RegionRepository extends CrudRepository<Region, Long> {
	
	List<Region> findAll();
	
	Region findOne(Long id);
	
}
