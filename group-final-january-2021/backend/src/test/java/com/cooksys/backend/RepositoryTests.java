package com.cooksys.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cooksys.backend.entities.CommonFields;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Credentials;
import com.cooksys.backend.entities.Profile;
import com.cooksys.backend.entities.Project;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.repositories.CompanyRepository;
import com.cooksys.backend.repositories.ProjectRepository;
import com.cooksys.backend.repositories.TeamRepository;
import com.cooksys.backend.repositories.UserRepository;

@SpringBootTest
class RepositoryTests {

	private static final Logger log = LoggerFactory.getLogger(RepositoryTests.class);
	private String companyName = "Test Company 1";

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	void setup() {
		Optional<Company> companyOptional = companyRepository.findByCommonFieldsName(companyName);
		if(companyOptional.isPresent()) {
			companyRepository.delete(companyOptional.get());
		}
	}
	
	@Test
	void createCompany() {
		Company company = getCompany(companyName);
		company = companyRepository.save(company);
		assertNotNull(company.getId(), "Company Id should not be null");
		assertEquals(companyName, company.getCommonFields().getName(), String.format("Company name is not equal to '%S'", companyName));
	}

	private Company getCompany(String companyName) {
		Company company = new Company(new CommonFields(companyName, String.format("this is the '%S'", companyName)));
		return company;
	}

	@Test
	void createTeam() {
		Company company = getCompany(companyName);
		company = companyRepository.save(company);
		assertNotNull(company.getId(), "Company Id should not be null");

		Team team = getTeam(company);
		team = teamRepository.save(team);
		log.debug("team id={}, companyid={}", team.getId(), company.getId());
		assertNotNull(team.getId(), "Team Id should not be null");
	}

	private Team getTeam(Company company) {
		Team team = new Team(company, new CommonFields("Team 1", "this is Team 1 in Test Company"));
		return team;
	}

	@Test
	void createProject() {
		Company company = getCompany(companyName);
		company = companyRepository.save(company);

		Team team = getTeam(company);
		team = teamRepository.save(team);
		log.debug("team id={}, companyid={}", team.getId(), company.getId());
		assertNotNull(team.getId(), "Team Id should not be null");

		Project project = new Project(team, new CommonFields("Project 1", "this is the project 1"));
		project = projectRepository.save(project);
		log.debug("project id={}, team id={}, companyid={}", project.getId(), team.getId(), company.getId());

		assertNotNull(project.getId(), "Project Id should not be null");
	}

	@Test
	void createUser() {
		Company company = getCompany(companyName);
		company = companyRepository.save(company);
		
		Team team = getTeam( company);
		teamRepository.saveAndFlush(team);

		User user1 = new User(null, new Credentials("test", "test"), new Profile("test", "test", team, company, null), null);
		user1 = userRepository.save(user1);
		assertNotNull(user1.getId(), "User Id should not be null");
		assertNotNull(user1.getProfile().getCompany(), "Company should not be null");
		assertTrue(user1.getProfile().getCompany().getId() == company.getId(), "Invalid Company id");

		User user = new User(null, new Credentials("test", "test"), new Profile("test", "test", team, company, null), null);
		Exception exception = assertThrows(RuntimeException.class, () -> {
			userRepository.save(user);
		});
		String expectedMessage = "could not execute statement; SQL [n/a]; constraint [uk_r43af9ap4edm43mmtq01oddj6]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
		String actualMessage = exception.getMessage();
		log.debug("actualMessage={}", actualMessage);
		assertTrue(actualMessage.contains(expectedMessage), "Should throw constrain violation");
	}
}
