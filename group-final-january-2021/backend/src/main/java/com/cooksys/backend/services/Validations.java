package com.cooksys.backend.services;

import java.util.Map;
import java.util.Optional;

import com.cooksys.backend.common.Constants;
import com.cooksys.backend.dtos.CompanyDto;
import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.RoleDto;
import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Role;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.repositories.UserRepository;

public class Validations {

	public void validateCompany(Optional<Company> optional) throws NotFoundException {
		validateEmpty(optional, "Company not found");
	}

	public void validateCompany(CompanyDto dto) throws InvalidRequestException {
		validateEmpty(dto, "Invalid company");
		validateEmpty(dto.getName(), "Invalid company name");
	}

	public void validateTeam(Optional<Team> optional) throws NotFoundException {
		validateEmpty(optional, "Team not found");
	}

	public void validateTeam(TeamDto dto) throws InvalidRequestException {
		validateEmpty(dto, "Invalid team");
		validateEmpty(dto.getName(), "Invalid team name");
	}

	public void validateTeamAndCompany(String teamName, String companyName) throws InvalidRequestException {
		validateEmpty(teamName, "Invalid team");
		validateEmpty(companyName, "Invalid company name");
	}

	public void validateRole(RoleDto dto) throws InvalidRequestException {
		validateEmpty(dto, "Invalid role");
		validateEmpty(dto.getName(), "Invalid role name");
	}

	public void validateEmpty(Optional<?> optional, String errorMessage) throws NotFoundException {
		if (optional.isEmpty()) {
			throw new NotFoundException(errorMessage);
		}
	}

	public void validateEmpty(Object object, String errorMessage) throws InvalidRequestException {
		if (object == null) {
			throw new InvalidRequestException(errorMessage);
		}
	}

	public User validateLoginCredentials(String action, Map<String, String> data, CredentialsDTO loginCredentials,
			UserRepository userRepository) throws InvalidCredentialsException, InvalidRequestException {
		if (loginCredentials == null) {
			throw new InvalidCredentialsException();
		}
		Optional<User> loginUserOptional = userRepository
				.findByCredentialsUsernameAndCredentialsPasswordAndDeletedFalse(loginCredentials.getUsername(),
						loginCredentials.getPassword());
		if (loginUserOptional.isEmpty()) {
			throw new InvalidCredentialsException();
		} else {
			Role userRole = loginUserOptional.get().getRole();
			if (userRole == null) {
				throw new InvalidCredentialsException("Role not defined");
			}
			String role;
			if (Constants.ACTION_USER_CREATE.equalsIgnoreCase(action)
					|| Constants.ACTION_USER_DELETE.equalsIgnoreCase(action)
					|| Constants.ACTION_TEAM_DELETE.equalsIgnoreCase(action)) {
				role = Constants.ROLE_ADMIN;
			} else if (Constants.ACTION_USER_UPDATE.equalsIgnoreCase(action)) {
				String updateUsername = data.get(Constants.PARAM_UPDATE_USERNAME);
				boolean isAdmin = Constants.ROLE_ADMIN.equalsIgnoreCase(loginUserOptional.get().getRole().getName());
				if (isAdmin) {
					role = Constants.ROLE_ADMIN;
				} else if (loginUserOptional.get().getCredentials().getUsername().equals(updateUsername)) {
					// internal users can only update their own profile
					role = null;
				} else {
					role = Constants.ROLE_ADMIN;
				}
			} else {
				throw new InternalError("Invalid action in Validations.validateLoginCredentials");
			}

			if (role != null) {
				if (!role.equalsIgnoreCase(userRole.getName())) {
					throw new InvalidCredentialsException("Invalid user role");
				}
			}
		}
		return loginUserOptional.get();
	}
}
