package com.cooksys.backend.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.backend.common.Constants;
import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.ProfileDto;
import com.cooksys.backend.dtos.UserDetailsDto;
import com.cooksys.backend.dtos.UserDto;
import com.cooksys.backend.dtos.UserRequestDto;
import com.cooksys.backend.entities.Company;
import com.cooksys.backend.entities.Credentials;
import com.cooksys.backend.entities.Role;
import com.cooksys.backend.entities.Team;
import com.cooksys.backend.entities.User;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.mappers.UserMapper;
import com.cooksys.backend.repositories.CompanyRepository;
import com.cooksys.backend.repositories.RoleRepository;
import com.cooksys.backend.repositories.TeamRepository;
import com.cooksys.backend.repositories.UserRepository;

@Service
public class UserService extends Validations {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private UserRepository userRepository;
	private UserMapper userMapper;

	private CompanyRepository companyRepository;
	private TeamRepository teamRepository;
	private RoleRepository roleRepository;

	public UserService(UserRepository userRepository, UserMapper userMapper, CompanyRepository companyRepository,
			TeamRepository teamRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;

		this.companyRepository = companyRepository;
		this.teamRepository = teamRepository;
		this.roleRepository = roleRepository;
	}

	public ResponseEntity<?> getUsers(String companyName) {
		List<User> user = userRepository.findByProfileCompanyCommonFieldsNameAndDeletedFalse(companyName);

		List<UserDto> userDtos = userMapper.entitiesToDtos(user);
		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
	}

	public ResponseEntity<?> login(CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
		Optional<User> userOptional = userRepository.findByCredentialsUsername(credentialsDTO.getUsername());
		validatePassword(credentialsDTO, userOptional);

		UserDto userDto = userMapper.entityToDto(userOptional.get());
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	public ResponseEntity<?> save(UserRequestDto userRequestDto)
			throws InvalidRequestException, DuplicateException, InvalidCredentialsException, NotFoundException {
		validateSaveUser(userRequestDto);

		User loginUser = validateLoginCredentials(Constants.ACTION_USER_CREATE, null, userRequestDto.getCredentials(),
				userRepository);

		UserDetailsDto userDetailsDto = userRequestDto.getUserDetails();
		CredentialsDTO userCredentials = userDetailsDto.getCredentials();
		String username = userCredentials.getUsername();

		User duplicateUser = validateDuplicateUser(username, userRepository);
		if (duplicateUser != null && duplicateUser.isDeleted()) {
			duplicateUser.setEnabled();
			duplicateUser = userRepository.saveAndFlush(duplicateUser);
			return new ResponseEntity<UserDto>(userMapper.entityToDto(duplicateUser), HttpStatus.OK);
		}

		User user = userMapper.requestDtoToEntity(userRequestDto);
		log.debug("user={}", user);

		UserDto userDto = userDetailsDto.getUser();
		Optional<Company> companyOptional = companyRepository
				.findByCommonFieldsName(userDto.getProfile().getCompany().getName());
		validateCompany(companyOptional);

		user.getProfile().setCompany(companyOptional.get());

		Optional<Team> teamOptional = teamRepository.findByCommonFieldsName(userDto.getProfile().getTeam().getName());
		validateTeam(teamOptional);
		user.getProfile().setTeam(teamOptional.get());

		Optional<Role> roleOptional = roleRepository.findByName(userDto.getRole().getName());
		validateRole(roleOptional);
		user.setRole(roleOptional.get());

		user.setUpdatedBy(loginUser);
		user = userRepository.saveAndFlush(user);

		return new ResponseEntity<UserDto>(userMapper.entityToDto(user), HttpStatus.CREATED);
	}

	public ResponseEntity<?> update(String username, UserRequestDto userRequestDto)
			throws InvalidCredentialsException, InvalidRequestException, DuplicateException, NotFoundException {

		Map<String, String> forCredentials = Map.of(Constants.PARAM_UPDATE_USERNAME, username);
		CredentialsDTO loginCredentials = userRequestDto.getCredentials();
		User loginUser = validateLoginCredentials(Constants.ACTION_USER_UPDATE, forCredentials, loginCredentials,
				userRepository);

		boolean isAdmin = Constants.ROLE_ADMIN.equalsIgnoreCase(loginUser.getRole().getName());
		validateUpdateUser(userRequestDto, isAdmin);

		UserDetailsDto userDetails = userRequestDto.getUserDetails();
		CredentialsDTO userCredentials = userDetails.getCredentials();
		String newUsername = userCredentials.getUsername();

		// remove trailing spaces
		userCredentials.setUsername(newUsername.trim());

		// if username has changed then validate duplicate
		if (!username.equals(newUsername)) {
			validateDuplicateUser(newUsername, userRepository);
		}

		// fetch original user
		Optional<User> userOptional = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		validateUser(userOptional);

		User user = userOptional.get();
		Credentials credentials = user.getCredentials();
		credentials.setUsername(userCredentials.getUsername());
		String newPassword = userCredentials.getPassword();
		if (newPassword != null && newPassword.trim().length() > 0) {
			// remove trailing spaces
			credentials.setPassword(newPassword.trim());
		}

		UserDto userDto = userDetails.getUser();
		ProfileDto profileDto = userDto.getProfile();
		user.getProfile().setFirstName(profileDto.getFirstName().trim());
		user.getProfile().setLastName(profileDto.getLastName().trim());

		if (isAdmin) {
			Optional<Company> companyOptional = companyRepository
					.findByCommonFieldsName(profileDto.getCompany().getName());
			validateCompany(companyOptional);

			user.getProfile().setCompany(companyOptional.get());

			Optional<Team> teamOptional = teamRepository.findByCommonFieldsName(profileDto.getTeam().getName());
			validateTeam(teamOptional);
			user.getProfile().setTeam(teamOptional.get());

			Optional<Role> roleOptional = roleRepository.findByName(userDto.getRole().getName());
			validateRole(roleOptional);
			user.setRole(roleOptional.get());
		}

		user.setUpdatedBy(loginUser);
		user = userRepository.saveAndFlush(user);
		return new ResponseEntity<UserDto>(userMapper.entityToDto(user), HttpStatus.OK);
	}

	public User getUserByUsername(String username) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
		validateUser(optionalUser);

		return optionalUser.get();
	}

	public User userFlush(User user) {
		User editedUser = userRepository.saveAndFlush(user);
		return editedUser;

	}

	public ResponseEntity<?> delete(String username, CredentialsDTO loginCredentials)
			throws NotFoundException, InvalidCredentialsException, InvalidRequestException {
		Optional<User> userOptional = userRepository.findByCredentialsUsername(username);
		validateUser(userOptional);

		User loginUser = validateLoginCredentials(Constants.ACTION_USER_DELETE, Map.of(), loginCredentials,
				userRepository);

		User user = userOptional.get();
		user.setDeleted();
		user.setUpdatedBy(loginUser);
		userRepository.saveAndFlush(user);

		return new ResponseEntity<UserDto>(userMapper.entityToDto(user), HttpStatus.OK);
	}

	private void validatePassword(CredentialsDTO credentialsDTO, Optional<User> userOptional)
			throws InvalidCredentialsException {
		if (credentialsDTO == null || userOptional.isEmpty()) {
			throw new InvalidCredentialsException("Invalid credentials.");
		}

		if (credentialsDTO.getUsername() == null || credentialsDTO.getPassword() == null
				|| !credentialsDTO.getPassword().equals(userOptional.get().getCredentials().getPassword())) {
			throw new InvalidCredentialsException("Invalid credentials.");
		}
	}

	private User validateDuplicateUser(String username, UserRepository userRepository) throws DuplicateException {
		Optional<User> userOptional = userRepository.findByCredentialsUsername(username);

		User user = null;
		if (userOptional.isPresent() && !userOptional.get().isDeleted()) {
			throw new DuplicateException("Duplicate username");
		} else if (userOptional.isPresent()) {
			user = userOptional.get();
		}
		return user;
	}

	private void validateRole(Optional<Role> optional) throws NotFoundException {
		validateEmpty(optional, "Role not found");
	}

	private void validateUser(Optional<User> optional) throws NotFoundException {
		validateEmpty(optional, "User not found");
	}

	private void validateSaveUser(UserRequestDto userRequestDto) throws InvalidRequestException {
		UserDetailsDto userDetailsDto = userRequestDto.getUserDetails();
		validateEmpty(userDetailsDto, "Invalid User details");
		validateEmpty(userDetailsDto.getUser(), "Invalid User");
		validateEmpty(userDetailsDto.getUser().getProfile(), "Invalid User Profile");

		validateCompany(userDetailsDto.getUser().getProfile().getCompany());
		validateTeam(userDetailsDto.getUser().getProfile().getTeam());
		validateRole(userDetailsDto.getUser().getRole());

		validateUserCredentials(userDetailsDto.getCredentials());

		CredentialsDTO loginCredentials = userRequestDto.getCredentials();
		validateLoginCredentials(loginCredentials);
	}

	private void validateUserCredentials(CredentialsDTO userCredentials) throws InvalidRequestException {
		validateUserCredentials(userCredentials, true);
	}

	private void validateUserCredentials(CredentialsDTO userCredentials, boolean isCheckPassword)
			throws InvalidRequestException {
		validateEmpty(userCredentials, "Invalid user credentials");
		validateEmpty(userCredentials.getUsername(), "Invalid user username");
		if (isCheckPassword) {
			validateEmpty(userCredentials.getPassword(), "Invalid user password");
		}
	}

	private void validateUpdateUser(UserRequestDto userRequestDto, boolean isAdmin) throws InvalidRequestException {
		UserDetailsDto userDetailsDto = userRequestDto.getUserDetails();
		validateEmpty(userDetailsDto, "Invalid User details");
		validateEmpty(userDetailsDto.getUser(), "Invalid User");
		validateEmpty(userDetailsDto.getUser().getProfile(), "Invalid User Profile");

		if (isAdmin) {
			validateCompany(userDetailsDto.getUser().getProfile().getCompany());
			validateTeam(userDetailsDto.getUser().getProfile().getTeam());
			validateRole(userDetailsDto.getUser().getRole());
		}

		validateUserCredentials(userDetailsDto.getCredentials(), false);

		CredentialsDTO loginCredentials = userRequestDto.getCredentials();
		validateLoginCredentials(loginCredentials);
	}

	private void validateLoginCredentials(CredentialsDTO loginCredentials) throws InvalidRequestException {
		if (loginCredentials == null || loginCredentials.getUsername() == null
				|| loginCredentials.getUsername().trim().length() == 0 || loginCredentials.getPassword() == null
				|| loginCredentials.getPassword().trim().length() == 0) {
			throw new InvalidRequestException("Invalid login credentials");
		}
	}
}
