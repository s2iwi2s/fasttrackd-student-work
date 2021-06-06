package com.cooksys.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.ProjectDto;
import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.dtos.UserDto;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.services.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {

	private TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping
	public ResponseEntity<List<TeamDto>> getTeams() {
		return new ResponseEntity<List<TeamDto>>(teamService.getTeamsDto(), HttpStatus.OK);
	}

	@GetMapping("/{companyName}/{teamName}")
	public ResponseEntity<TeamDto> getTeam(@PathVariable String teamName, @PathVariable String companyName)
			throws NotFoundException, InvalidRequestException {
		return new ResponseEntity<TeamDto>(teamService.getTeamDto(teamName, companyName), HttpStatus.OK);
	}

	@GetMapping("/{companyName}/{teamName}/users")
	public ResponseEntity<List<UserDto>> getTeamMembers(@PathVariable String teamName, @PathVariable String companyName)
			throws NotFoundException {
		return new ResponseEntity<List<UserDto>>(teamService.getTeamMembersDto(teamName, companyName), HttpStatus.OK);
	}

	@GetMapping("/{companyName}/{teamName}/projects")
	public ResponseEntity<List<ProjectDto>> getTeamProjects(@PathVariable String teamName,
			@PathVariable String companyName) throws NotFoundException {
		return new ResponseEntity<List<ProjectDto>>(teamService.getTeamProjectsDto(teamName, companyName),
				HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{companyName}/{teamCurrentName}/{teamNewName}")
	public ResponseEntity<TeamDto> setTeamName(@PathVariable String teamCurrentName, @PathVariable String teamNewName,
			@PathVariable String companyName) throws NotFoundException, InvalidRequestException, DuplicateException {
		return new ResponseEntity<TeamDto>(teamService.setTeamNameDto(teamCurrentName, teamNewName, companyName),
				HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{companyName}/{teamName}/text")
	public ResponseEntity<TeamDto> setTeamText(@PathVariable String teamName, @PathVariable String companyName,
			@RequestBody String text) throws NotFoundException {
		return new ResponseEntity<TeamDto>(teamService.setTeamTextDto(teamName, companyName, text), HttpStatus.OK);
	}

	@PostMapping("/{companyName}/{teamName}/projects")
	public ResponseEntity<TeamDto> addTeamProject(@PathVariable String teamName, @PathVariable String companyName,
			@RequestBody ProjectDto project) throws NotFoundException, DuplicateException, InvalidRequestException {
		return new ResponseEntity<TeamDto>(teamService.addTeamProjectDto(teamName, companyName, project),
				HttpStatus.CREATED);
	}

	@DeleteMapping("{companyName}/{teamName}")
	public ResponseEntity<TeamDto> deleteTeam(@PathVariable String companyName, @PathVariable String teamName,
			@RequestBody CredentialsDTO creds)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException {
		return new ResponseEntity<TeamDto>(teamService.deleteTeam(creds, teamName, companyName), HttpStatus.OK);
	}

}
