package com.cooksys.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.backend.dtos.CompanyDto;
import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.mappers.CompanyMapper;
import com.cooksys.backend.mappers.TeamMapper;
import com.cooksys.backend.repositories.CompanyRepository;

@Service
public class CompanyService extends Validations {

	private CompanyRepository companyRepository;
	private CompanyMapper companyMapper;
	private TeamService teamService;
	private TeamMapper teamMapper;

	public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, TeamService teamService,
			TeamMapper teamMapper) {
		this.companyRepository = companyRepository;
		this.companyMapper = companyMapper;
		this.teamService = teamService;
		this.teamMapper = teamMapper;
	}

	private List<Company> getCompanies() {
		System.out.println(companyRepository.findAll());
		return companyRepository.findAll();

	}

	private Company addCompany(CompanyDto newCompany) throws InvalidRequestException, DuplicateException {
		validateAddCompany(newCompany, companyRepository);
		Company company = companyMapper.dtoToEntity(newCompany);
		company.setTeams(new ArrayList<Team>());
		company.setUsers(new ArrayList<User>());
		Company createdCompany = companyRepository.saveAndFlush(company);
		return createdCompany;

	}

	private Company getCompany(String companyName) throws NotFoundException {
		Optional<Company> optionalCompany = companyRepository.findByCommonFieldsName(companyName);
		validateCompany(optionalCompany);
		return optionalCompany.get();
	}

	private List<Team> getCompanyTeams(String companyName) throws NotFoundException {
		Company company = getCompany(companyName);
		// return teams filtered by not deleted.
		return company.getTeams().stream().filter(t -> !t.isDeleted()).collect(Collectors.toList());
	}

	private Company setCompanyName(String currentName, String newName)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		Company company = getCompany(currentName);

		validateUpdateCompany(currentName, newName, companyRepository);

		company.getCommonFields().setName(newName);
		Company createdCompany = companyRepository.saveAndFlush(company);
		return createdCompany;
	}

	private Company setCompanyText(String companyName, String companyText) throws NotFoundException {
		Company company = getCompany(companyName);
		company.getCommonFields().setText(companyText);
		Company createdCompany = companyRepository.saveAndFlush(company);
		return createdCompany;
	}

	private Company addCompanyTeam(String companyName, TeamDto newTeam)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		Company company = getCompany(companyName);

		Team team = teamService.addTeam(newTeam, company);
		company.getTeams().add(team);
		Company createdCompany = companyRepository.saveAndFlush(company);
		return createdCompany;
	}

	public List<CompanyDto> getCompaniesDto() {
		return companyMapper.entitiesToDtos(getCompanies());
	}

	public CompanyDto getCompanyDto(String companyName) throws NotFoundException {
		return companyMapper.entityToDto(getCompany(companyName));
	}

	public CompanyDto addCompanyDto(CompanyDto company) throws InvalidRequestException, DuplicateException {
		return companyMapper.entityToDto(addCompany(company));
	}

	public List<TeamDto> getCompanyTeamsDto(String companyName) throws NotFoundException {
		return teamMapper.entitiesToDtos(getCompanyTeams(companyName));
	}

	public CompanyDto setCompanyNameDto(String currentName, String newName)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return companyMapper.entityToDto(setCompanyName(currentName, newName));
	}

	public CompanyDto setCompanyTextDto(String companyName, String companyText) throws NotFoundException {
		return companyMapper.entityToDto(setCompanyText(companyName, companyText));
	}

	public CompanyDto addCompanyTeamDto(String companyName, TeamDto newTeam)
			throws NotFoundException, InvalidRequestException, DuplicateException {
		return companyMapper.entityToDto(addCompanyTeam(companyName, newTeam));
	}

	private void validateAddCompany(CompanyDto newCompany, CompanyRepository companyRepository)
			throws InvalidRequestException, DuplicateException {
		if (newCompany.getName() == null || newCompany.getName().trim().length() == 0) {
			throw new InvalidRequestException("Invalid company name");
		}
		Optional<Company> companyOptional = companyRepository.findByCommonFieldsName(newCompany.getName());
		if (companyOptional.isPresent()) {
			throw new DuplicateException("Duplicate company name");
		}
	}

	private void validateUpdateCompany(String currentName, String newName, CompanyRepository companyRepository)
			throws InvalidRequestException, DuplicateException {
		if (newName == null || newName.trim().length() == 0) {
			throw new InvalidRequestException("Invalid company name");
		}
		if (!currentName.equals(newName)) {
			Optional<Company> optionalCompany = companyRepository.findByCommonFieldsName(newName);
			if (optionalCompany.isPresent()) {
				throw new DuplicateException("Duplicate company name");
			}
		}
	}
}
