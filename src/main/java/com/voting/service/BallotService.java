package com.voting.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.repository.BallotRepository;
import com.voting.repository.ElectionRepository;
import com.voting.repository.RegionRepository;

import com.voting.domain.Ballot;
import com.voting.domain.Region;
import com.voting.domain.Election;

@Component
@Controller
@Path("/ballot")
@Produces(value="application/json")
@Consumes(value="application/json")
public class BallotService {
	
	private BallotRepository ballotRepo;
	private RegionRepository regionRepo;
	private ElectionRepository electionRepo;
	
	@Autowired
	public void setBallotDao(final BallotRepository repo) {
		this.ballotRepo = repo;
	}
	
	@Autowired
	public void setRegionDao(final RegionRepository repo) {
		this.regionRepo = repo;
	}
	
	@Autowired
	public void setElectionDao(final ElectionRepository repo) {
		this.electionRepo = repo;
	}
	
	@GET
	public List<Ballot> getAll() {

		List<Ballot> ballots = ballotRepo.findAll();		
			
		if(ballots.isEmpty()) {
				
			saveBallots();
				
		}
		
		return ballotRepo.findAll();
		
	}
	
	@GET
	@Path("/byelection/{electionid}")
	public Ballot getByElectionId(@PathParam("electionid") Long electionId) {
		
		List<Ballot> ballots = ballotRepo.findByElectionId(electionId);

		for(int i = 0; i < ballots.size(); i++) {

			Ballot b = ballots.get(i);

			if(true) {
				return b;
			}

		}

		return null;
		
	}
	
	@GET
	@Path("/byregion/{regionid}")
	public List<Ballot> getByRegionId(@PathParam("regionid") Long regionId) {
		
		return ballotRepo.findByRegionId(regionId);
		
	}
	
	public void saveBallots() {
		
		List<Region> regions = regionRepo.findAll();
		
		for(Region r: regions) {
			Long regionId = r.getId();
			List<Election> elections = electionRepo.findAll();
			for(Election e: elections) {
				Long electionId = e.getId();
				Ballot newBallot = new Ballot();
				newBallot.setElectionId(electionId);
				newBallot.setRegionId(regionId);
				ballotRepo.save(newBallot);	
			}
		}
	}

}
