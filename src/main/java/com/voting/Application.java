package com.voting;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
