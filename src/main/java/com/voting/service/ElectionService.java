package com.voting.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.domain.Election;
import com.voting.repository.ElectionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
@Controller
@Path("/election")
@Produces(value="application/json")
@Consumes(value="application/json")
public class ElectionService{

	ElectionRepository electionRepo;

	@Autowired
	public void setElectionDao(final ElectionRepository repo) {
		this.electionRepo = repo;
	}

	@POST
	public Election registerElection(Election election) {

		return electionRepo.save(election);

	}
	
	@GET
	public List<Election> getAllElections() {
		
		List<Election> elections = electionRepo.findAll();
		
		if(elections.size() < 1) {
			Election only = new Election();
			SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
			
			try {
				
				Date startDate = dateformat.parse("12/04/2017");
				Date endDate = dateformat.parse("19/04/2017");
				only.setStartDate(startDate);
				only.setEndDate(endDate);
				electionRepo.save(only);
			
			} catch (ParseException e) {
	            e.printStackTrace();
			}
			
			elections = electionRepo.findAll();

		}
		
		return elections;
		
	}
	
}


