package com.cooksys.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.backend.common.Constants;
import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.ProjectDto;
import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.dtos.UserDto;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Project;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.mappers.ProjectMapper;
import com.cooksys.backend.mappers.TeamMapper;
import com.cooksys.backend.mappers.UserMapper;
import com.cooksys.backend.repositories.TeamRepository;
import com.cooksys.backend.repositories.UserRepository;

@Service
public class TeamService extends Validations {
	private TeamRepository teamRepository;
	private TeamMapper teamMapper;
	private UserRepository userRepository;
	private UserMapper userMapper;
	private ProjectService projectService;
	private ProjectMapper projectMapper;

	public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, UserRepository userRepository,
			UserMapper userMapper, ProjectService projectService, ProjectMapper projectMapper) {
		this.teamRepository = teamRepository;
		this.teamMapper = teamMapper;
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.projectService = projectService;
		this.projectMapper = projectMapper;
	}

	private List<Team> getTeams() {
		return teamRepository.findAll();

	}

	public Team getTeam(String teamName, String companyName) throws NotFoundException {
		Optional<Team> optionalTeam = teamRepository.findByCommonFieldsNameAndCompanyCommonFieldsName(teamName,
				companyName);
		validateTeam(optionalTeam);
		if (!optionalTeam.get().isDeleted()) {
			return optionalTeam.get();
		} else {
			throw new NotFoundException("Team not found!");
		}
	}

	public Team addTeam(TeamDto newTeam, Company company) throws InvalidRequestException, DuplicateException {
		validateCreateTeam(newTeam, company, teamRepository);

		Team team = teamMapper.dtoToEntity(newTeam);
		team.setUsers(new ArrayList<User>());
		team.setProjects(new ArrayList<Project>());
		team.setCompany(company);
		Team createdTeam = teamRepository.saveAndFlush(team);
		return createdTeam;

	}

	private List<User> getTeamMembers(String teamName, String companyName) throws NotFoundException {
		Team team = getTeam(teamName, companyName);
		List<User> teamMembers = team.getUsers();

		if (teamMembers.isEmpty()) {
			return null;
		} else {
			return teamMembers.stream().filter(u -> !u.isDeleted()).collect(Collectors.toList());
		}
	}

	private List<Project> getTeamProjects(String teamName, String companyName) throws NotFoundException {
		Team team = getTeam(teamName, companyName);
		List<Project> teamProjects = team.getProjects();
		if (teamProjects.isEmpty()) {
			return null;
		} else {
			return teamProjects.stream().filter(p -> !p.isDeleted()).collect(Collectors.toList());
		}
	}

	private Team setTeamName(String teamCurrentName, String teamNewName, String companyName)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		Team team = getTeam(teamCurrentName, companyName);

		validateUpdateTeamName(teamCurrentName, teamNewName, companyName, teamRepository);

		team.getCommonFields().setName(teamNewName);
		Team createdTeam = teamRepository.saveAndFlush(team);
		return createdTeam;
	}

	private Team setTeamText(String teamName, String companyName, String teamText) throws NotFoundException {
		Team team = getTeam(teamName, companyName);
		team.getCommonFields().setText(teamText);
		Team createdTeam = teamRepository.saveAndFlush(team);
		return createdTeam;
	}

	private Team addTeamProject(String teamName, String companyName, ProjectDto project)
			throws NotFoundException, DuplicateException, InvalidRequestException {
		Team team = getTeam(teamName, companyName);

		Project newProject = projectService.addProject(project, team);

		team.getProjects().add(newProject);
		Team editedTeam = teamRepository.saveAndFlush(team);
		return editedTeam;
	}

	public List<TeamDto> getTeamsDto() {
		return teamMapper.entitiesToDtos(getTeams());
	}

	public TeamDto getTeamDto(String teamName, String companyName) throws NotFoundException {
		return teamMapper.entityToDto(getTeam(teamName, companyName));
	}

	public List<UserDto> getTeamMembersDto(String teamName, String companyName) throws NotFoundException {
		return userMapper.entitiesToDtos(getTeamMembers(teamName, companyName));
	}

	public List<ProjectDto> getTeamProjectsDto(String teamName, String companyName) throws NotFoundException {
		return projectMapper.entitiesToDtos(getTeamProjects(teamName, companyName));
	}

	public TeamDto setTeamNameDto(String teamCurrentName, String teamNewName, String companyName)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return teamMapper.entityToDto(setTeamName(teamCurrentName, teamNewName, companyName));
	}

	public TeamDto setTeamTextDto(String teamName, String companyName, String teamText) throws NotFoundException {
		return teamMapper.entityToDto(setTeamText(teamName, companyName, teamText));
	}

	public TeamDto addTeamProjectDto(String teamName, String companyName, ProjectDto project)
			throws NotFoundException, DuplicateException, InvalidRequestException {
		return teamMapper.entityToDto(addTeamProject(teamName, companyName, project));
	}

	public TeamDto deleteTeam(CredentialsDTO creds, String teamName, String companyName)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException {
		validateLoginCredentials(Constants.ACTION_PROJECT_DELETE, Map.of(), creds, userRepository);

		Team team = getTeam(teamName, companyName);

		// Delete the team
		team.setDeleted(true);
		teamRepository.saveAndFlush(team);
		return teamMapper.entityToDto(team);

	}

	private void validateCreateTeam(TeamDto newTeam, Company company, TeamRepository teamRepository)
			throws InvalidRequestException, DuplicateException {
		if (newTeam.getName() == null || newTeam.getName().trim().length() == 0) {
			throw new InvalidRequestException("Invalid Team name");
		}
		Optional<Team> teamOptional = teamRepository.findByCommonFieldsNameAndCompanyCommonFieldsName(newTeam.getName(),
				company.getCommonFields().getName());
		if (teamOptional.isPresent()) {
			throw new DuplicateException("Duplicate team name");
		}
	}

	private void validateUpdateTeamName(String teamCurrentName, String teamNewName, String companyName,
			TeamRepository teamRepository) throws InvalidRequestException, DuplicateException {
		validateEmpty(teamNewName, "Invalid Team name");

		if (!teamCurrentName.equals(teamNewName)) {
			Optional<Team> optionalTeam = teamRepository.findByCommonFieldsNameAndCompanyCommonFieldsName(teamNewName,
					companyName);
			if (optionalTeam.isPresent()) {
				throw new DuplicateException("Duplicate Team name");
			}
		}
	}
}
