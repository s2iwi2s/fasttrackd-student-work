package com.cooksys.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cooksys.backend.common.Constants;
import com.cooksys.backend.entities.CommonFields;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Credentials;
import com.cooksys.backend.entities.Profile;
import com.cooksys.backend.entities.Project;
import com.cooksys.backend.entities.Role;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.repositories.CompanyRepository;
import com.cooksys.backend.repositories.ProjectRepository;
import com.cooksys.backend.repositories.RoleRepository;
import com.cooksys.backend.repositories.TeamRepository;
import com.cooksys.backend.repositories.UserRepository;

@SpringBootApplication
public class WikkiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikkiAppApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	public CommandLineRunner runner(CompanyRepository companyRepository, TeamRepository teamRepository,
			ProjectRepository projectRepository, UserRepository userRepository, RoleRepository roleRepository) {
		return (args) -> {
			// Create company
			Company company = new Company(new CommonFields("company 1", "this is the company 1"));
			companyRepository.saveAndFlush(company);

			// Create team
			Team team = new Team(company, new CommonFields("Team 1", "this is the Team 1 in Company 1"));
			team = teamRepository.saveAndFlush(team);
			Team team2 = new Team(company, new CommonFields("Team 2", "this is the Team 2 in Company 1"));
			teamRepository.saveAndFlush(team2);

			Role role1 = roleRepository.findByName(Constants.ROLE_ADMIN).get();
			Role role2 = roleRepository.findByName(Constants.ROLE_INTERNAL).get();

			// Create users
			User user1 = new User(null, new Credentials("admin", "test"),
					new Profile("test", "test", team, company, null), role1);
			user1 = userRepository.saveAndFlush(user1);

			User user2 = new User(null, new Credentials("internal", "test"),
					new Profile("test", "test", team, company, null), role2);
			user2 = userRepository.saveAndFlush(user2);

			// Create project
			Project project = new Project(team, new CommonFields("Project 1", "this is the project 1"));
			project = projectRepository.saveAndFlush(project);
			


			// add project to user
			user2.getProfile().addUser(project);
			user2 = userRepository.saveAndFlush(user2);
		};
	}
}
