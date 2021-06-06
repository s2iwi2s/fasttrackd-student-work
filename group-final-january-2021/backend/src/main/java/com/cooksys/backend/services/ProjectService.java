package com.cooksys.backend.services;

import com.cooksys.backend.common.Constants;
import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.ProjectDto;
import com.cooksys.backend.entities.Project;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.mappers.ProjectMapper;
import com.cooksys.backend.repositories.TeamRepository;
import com.cooksys.backend.repositories.UserRepository;

import org.springframework.stereotype.Service;

import com.cooksys.backend.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService extends Validations {

	private ProjectRepository projectRepository;
	private ProjectMapper projectMapper;
	private UserService userService;
	private UserRepository userRepository;
	private TeamRepository teamRepository;

	public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService,
			UserRepository userRepository, TeamRepository teamRepository) {
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
		this.userService = userService;
		this.userRepository = userRepository;
		this.teamRepository = teamRepository;
	}

	private List<Project> getProjects() {
		return projectRepository.findAll();
	}

	public Project addProject(ProjectDto project, Team team) throws DuplicateException, InvalidRequestException {
		validateAddProject(project, team, projectRepository);

		Project newProject = projectMapper.dtoToEntity(project);
		newProject.setTeam(team);
		newProject.setUsers(new ArrayList<User>());
		Project createdProject = projectRepository.saveAndFlush(newProject);
		for (User u : team.getUsers()) {
			u.getProfile().getProjects().add(createdProject);
			createdProject.getUsers().add(u);
			userService.userFlush(u);
		}
		projectRepository.saveAndFlush(createdProject);
		return createdProject;
	}

	public Project getProject(String projectName, String teamName, String companyName) throws NotFoundException {
		Team team = getTeam(teamName, companyName);
		Optional<Project> optionalProject = projectRepository.findByCommonFieldsNameAndTeamId(projectName,
				team.getId());
		if (optionalProject.isPresent() && !optionalProject.get().isDeleted()) {
			return optionalProject.get();
		} else {
			throw new NotFoundException("Project Not Found!");
		}
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

	private Project setProjectName(String currentProjectName, String newProjectName, String teamName,
			String companyName) throws NotFoundException {
		Project project = getProject(currentProjectName, teamName, companyName);
		project.getCommonFields().setName(newProjectName);
		Project createdProject = projectRepository.saveAndFlush(project);
		return createdProject;

	}

	private Project setProjectText(String projectName, String teamName, String companyName, String text)
			throws NotFoundException {
		Project project = getProject(projectName, teamName, companyName);
		project.getCommonFields().setText(text);
		Project createdProject = projectRepository.saveAndFlush(project);
		return project;

	}

	public List<ProjectDto> getProjectsDto() {
		return projectMapper.entitiesToDtos(getProjects());
	}

	public ProjectDto setProjectNameDto(String currentProjectName, String newProjectName, String teamName,
			String companyName) throws NotFoundException {
		return projectMapper.entityToDto(setProjectName(currentProjectName, newProjectName, teamName, companyName));
	}

	public ProjectDto setProjectTextDto(String projectName, String teamName, String companyName, String projectText)
			throws NotFoundException {
		return projectMapper.entityToDto(setProjectText(projectName, teamName, companyName, projectText));
	}

	public ProjectDto deleteProject(String companyName, String teamName, String projectName, CredentialsDTO creds)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException {
		validateLoginCredentials(Constants.ACTION_TEAM_DELETE, Map.of(), creds, userRepository);
		Project project = getProject(projectName, teamName, companyName);
		project.setDeleted(true);
		projectRepository.saveAndFlush(project);
		return projectMapper.entityToDto(project);
	}

	private void validateAddProject(ProjectDto project, Team team, ProjectRepository projectRepository)
			throws DuplicateException, InvalidRequestException {
		validateEmpty(project.getName(), "Invalid project name");

		Optional<Project> projectOptional = projectRepository.findByCommonFieldsNameAndTeamId(project.getName(),
				team.getId());
		if (projectOptional.isPresent()) {
			throw new DuplicateException("Duplicate project name");
		}
	}
}
