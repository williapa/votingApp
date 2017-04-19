package com.voting.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

import com.voting.service.VoterService;
import com.voting.service.BallotQuestionService;
import com.voting.service.BallotService;
import com.voting.service.CandidateService;
import com.voting.service.ElectionService;
import com.voting.service.QuestionService;
import com.voting.service.RegionService;
import com.voting.service.VoteService;

@Component
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration(){
		super();
		this.registerEndPoints();
	}

	private void registerEndPoints() {
		this.register(VoterService.class);
		this.register(ElectionService.class);
		this.register(BallotService.class);
		this.register(BallotQuestionService.class);
		this.register(QuestionService.class);
		this.register(RegionService.class);
		this.register(VoteService.class);
		this.register(CandidateService.class);
	}
}