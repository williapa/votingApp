package com.voting.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.voting.repository.RegionRepository;
import com.voting.domain.Region;

@Component
@Controller
@Path("/region")
@Produces(value="application/json")
@Consumes(value="application/json")
public class RegionService {
	
	private RegionRepository regionRepo;
	
	@Autowired
	public void setRegionDao(final RegionRepository repo) {
		this.regionRepo = repo;
	}
	
	@GET
	public List<Region> getAll() {
		
		return regionRepo.findAll();
		
	}
	
	@PostConstruct
	public void saveRegions() {
		
		String[] regionNames = {"Southwest", "Northwest", "Southeast", "Northeast", "Midwest"};
		
		for(int i = 0; i < regionNames.length; i++) {
			
			Region newRegion = new Region();
			
			newRegion.setRegion(regionNames[i]);
			
			regionRepo.save(newRegion);
			
		}
	}

}
