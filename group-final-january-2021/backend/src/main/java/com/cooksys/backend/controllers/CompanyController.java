package com.cooksys.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooksys.backend.dtos.CompanyDto;
import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.services.CompanyService;
import com.cooksys.backend.services.UserService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private CompanyService companyService;
	private UserService userService;

	public CompanyController(CompanyService companyService, UserService userService) {
		this.companyService = companyService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<CompanyDto>> getCompanies() {
		return new ResponseEntity<List<CompanyDto>>(companyService.getCompaniesDto(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto company)
			throws InvalidRequestException, DuplicateException {
		return new ResponseEntity<CompanyDto>(companyService.addCompanyDto(company), HttpStatus.CREATED);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable String name) throws NotFoundException {
		return new ResponseEntity<CompanyDto>(companyService.getCompanyDto(name), HttpStatus.OK);
	}

	@GetMapping("/{name}/teams")
	public ResponseEntity<List<TeamDto>> getCompanyTeams(@PathVariable String name) throws NotFoundException {
		return new ResponseEntity<List<TeamDto>>(companyService.getCompanyTeamsDto(name), HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{currentName}/{newName}")
	public ResponseEntity<CompanyDto> setCompanyName(@PathVariable String currentName, @PathVariable String newName)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return new ResponseEntity<CompanyDto>(companyService.setCompanyNameDto(currentName, newName), HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PatchMapping("/{name}/text")
	public ResponseEntity<CompanyDto> setCompanyText(@PathVariable String name, @RequestBody String text)
			throws NotFoundException {
		return new ResponseEntity<CompanyDto>(companyService.setCompanyTextDto(name, text), HttpStatus.OK);
	}

	@PostMapping("/{name}/teams")
	public ResponseEntity<CompanyDto> addCompanyTeam(@PathVariable String name, @RequestBody TeamDto newTeam)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return new ResponseEntity<CompanyDto>(companyService.addCompanyTeamDto(name, newTeam), HttpStatus.CREATED);
	}

	@GetMapping("/{name}/users")
	public ResponseEntity<?> getCompanyUsers(@PathVariable String name)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return userService.getUsers(name);
	}
}
