package com.cooksys.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.ProjectDto;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.services.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping
	public ResponseEntity<List<ProjectDto>> getProjects() {
		return new ResponseEntity<List<ProjectDto>>(projectService.getProjectsDto(), HttpStatus.OK);
	}

    @CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{companyName}/{teamName}/{projectCurrentName}/{projectNewName}")
	public ResponseEntity<ProjectDto> setProjectName(@PathVariable String projectCurrentName,
			@PathVariable String projectNewName, @PathVariable String teamName, @PathVariable String companyName)
			throws NotFoundException {
		return new ResponseEntity<ProjectDto>(
				projectService.setProjectNameDto(projectCurrentName, projectNewName, teamName, companyName),
				HttpStatus.OK);
	}

    @CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{companyName}/{teamName}/{projectName}/text")
	public ResponseEntity<ProjectDto> setProjectText(@PathVariable String projectName, @PathVariable String teamName,
			@PathVariable String companyName, @RequestBody String text) throws NotFoundException {
		return new ResponseEntity<ProjectDto>(
				projectService.setProjectTextDto(projectName, teamName, companyName, text), HttpStatus.OK);
	}

	@DeleteMapping("/{companyName}/{teamName}/{projectName}")
	public ResponseEntity<ProjectDto> deleteProject(@PathVariable String companyName, @PathVariable String teamName,
			@PathVariable String projectName, @RequestBody CredentialsDTO creds)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException {
		return new ResponseEntity<ProjectDto>(projectService.deleteProject(companyName, teamName, projectName, creds),
				HttpStatus.OK);
	}
}
