package com.voting.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.voting.domain.Question;

public interface QuestionRepository extends CrudRepository<Question, Long>{
	
	List<Question> findAll();
	
	Question findById(Long id);
	
	Question findByBody(String body);	

}
