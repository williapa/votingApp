package com.voting.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.domain.Region;
import com.voting.domain.Voter;
import com.voting.repository.RegionRepository;
import com.voting.repository.VoterRepository;

import com.voting.security.WebTokenEncryption;

import java.math.BigInteger;

@Component
@Controller
@Path("/voter")
@Produces(value="application/json")
@Consumes(value="application/json")
public class VoterService{

	private VoterRepository voterRepo;
	private RegionRepository regionRepo;
	
	@Autowired
	private WebTokenEncryption crypt;
	
	@Autowired
	public void setVoterDao(final VoterRepository repo){
		this.voterRepo = repo;
	}
	
	@Autowired
	public void setRegionDao(final RegionRepository repo){
		this.regionRepo = repo;
	}

	@POST
	@Path("/signup")
	public Voter registerVoter(Voter voter) {

		boolean success = saveVoter(voter.getName(), voter.getPassword(), voter.getRegionId());
		
		if(success) {

			Voter v = voterRepo.findByName(voter.getName());
			v.setPassword(crypt.encrypt(Long.toString(v.getId())));

			return voter;

		}

		return null;

	}
	
	@POST
	@Path("/login")
	public Voter login(Voter v) {
		
		String name= v.getName();
		String password = v.getPassword();
		
		Voter tryingToSignIn = voterRepo.findByName(name);
		
		if ( tryingToSignIn != null) {
			
			return null;
			
		} else {
			
			Voter user = tryingToSignIn;
			
			if (user.getPassword().equals(password) ) {

				user.setPassword(crypt.encrypt(Long.toString(user.getId())));
				return user;
				 
			} 
			
			return null;
		}
		
	}
	
	
	@GET
	public List<Voter> getAllVoters() {
		
		List<Voter> voters = voterRepo.findAll();
		
		if(voters.isEmpty()) {
			
			saveVoter("Paul Williams", "password", 1L);
			saveVoter("Chip Reese", "password", 2L);
			saveVoter("Mike Caro", "password", 3L);
			saveVoter("Phil Ivey", "password", 4L);
			saveVoter("Crocodile Dundee", "password", 5L);
			saveVoter("Leo Pena", "password", 1L);
			saveVoter("Ronald Grump", "password", 2L);
			saveVoter("Todd Glass", "password", 3L);
			saveVoter("Scott Aukerman", "password", 4L);
			saveVoter("Armie Hammer", "password", 5L);
			saveVoter("Jeff Stern", "password", 1L);
			saveVoter("Quentin Tarantino", "password", 2L);
			saveVoter("Chad LeFountain", "password", 3L);
			saveVoter("Jon Glenn", "password", 4L);
			saveVoter("Roger Moore", "password", 5L);
			voters = voterRepo.findAll();
		}
		
		return voters;
	}
	
	public boolean saveVoter(String name, String password, Long regionId) {
		
		Voter voter = new Voter();
		Voter sameName = voterRepo.findByName(name); 
		Region exists = regionRepo.findOne(regionId);
		
		if(exists != null) {
		
			voter.setName(name);
			voter.setPassword(password);
			voter.setRegionId(regionId);
			voterRepo.save(voter);
			return true;
		}
		
		return false;
		
	}


}


