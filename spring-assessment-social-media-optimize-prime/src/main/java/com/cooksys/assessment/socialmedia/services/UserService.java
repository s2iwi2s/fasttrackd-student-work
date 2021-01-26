package com.cooksys.assessment.socialmedia.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.assessment.socialmedia.common.Validations;
import com.cooksys.assessment.socialmedia.dtos.CredentialsDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.dtos.UserRequestDto;
import com.cooksys.assessment.socialmedia.dtos.UserResponseDto;
import com.cooksys.assessment.socialmedia.entities.Tweet;
import com.cooksys.assessment.socialmedia.entities.User;
import com.cooksys.assessment.socialmedia.exceptions.DuplicateException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidCredentialsException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidRequestException;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.mappers.TweetMapper;
import com.cooksys.assessment.socialmedia.mappers.UserMapper;
import com.cooksys.assessment.socialmedia.repositories.TweetRepository;
import com.cooksys.assessment.socialmedia.repositories.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private UserMapper userMapper;

	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private Validations validations;

	public UserService(UserRepository userRepository, UserMapper userMapper, TweetRepository tweetRepository,
			TweetMapper tweetMapper, Validations validations) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.validations = validations;
	}

	public ResponseEntity<?> getUsers() {
		Set<User> list = userRepository.findByActiveTrue();
		return new ResponseEntity<Set<UserResponseDto>>(userMapper.entitiesToResponseDtos(list), HttpStatus.OK);
	}

	public ResponseEntity<?> create(UserRequestDto userDto)
			throws InvalidCredentialsException, InvalidRequestException, DuplicateException {
//		validations.validateCredentials(userDto);
		validations.validateProfile(userDto);

		Optional<User> optionalUser = userRepository.findByCredentialsUsername(userDto.getCredentials().getUsername());
		User user;
		if (optionalUser.isEmpty()) {
			user = userMapper.requestDtoToEntity(userDto);
		} else if (optionalUser.get().getCredentials().getUsername()
				.equalsIgnoreCase(userDto.getCredentials().getUsername()) && !optionalUser.get().isActive()) {
			user = optionalUser.get();
		} else {
			throw new DuplicateException("Duplicate username");
		}
		user.setActive(true);
		return new ResponseEntity<UserResponseDto>(userMapper.entityToResponsetDto(userRepository.saveAndFlush(user)),
				HttpStatus.CREATED);
	}

	public ResponseEntity<?> update(String username, UserRequestDto userDto)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException, DuplicateException {
//		validations.validateCredentials(userDto);
		validations.validateProfile(userDto);

		Optional<User> optionalCurrentUser = userRepository
				.findByCredentialsUsernameAndActive(userDto.getCredentials().getUsername(), true);
		validations.validateCredentials(userDto.getCredentials(), optionalCurrentUser);

		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActive(username, true);
		User user;
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("No such user exist");
		}
		user = optionalUser.get();
		user.setProfile(userMapper.profiletoToEntity(userDto.getProfile()));

		return new ResponseEntity<UserResponseDto>(userMapper.entityToResponsetDto(userRepository.saveAndFlush(user)),
				HttpStatus.CREATED);
	}

	public ResponseEntity<?> getUser(String username) throws NotFoundException {
		Optional<User> optionalUser = findByUsername(username);
		return new ResponseEntity<UserResponseDto>(userMapper.entityToResponsetDto(optionalUser.get()), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteUser(String username, CredentialsDto credentials)
			throws InvalidCredentialsException, NotFoundException {
		validations.validateCredentials(credentials);

		Optional<User> optionalCurrentUser = userRepository
				.findByCredentialsUsernameAndActive(credentials.getUsername(), true);
		validations.validateCredentials(credentials, optionalCurrentUser);

		Optional<User> optionalUser = findByUsername(username);

		User user = optionalUser.get();
		user.setActive(false);
		user = userRepository.saveAndFlush(user);

		return new ResponseEntity<UserResponseDto>(userMapper.entityToResponsetDto(user), HttpStatus.OK);
	}

	private Optional<User> findByUsername(String username) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
		if (optionalUser.isEmpty() || (optionalUser.isPresent() && !optionalUser.get().isActive())) {
			throw new NotFoundException("No such user exists");
		}
		return optionalUser;
	}

	public ResponseEntity<?> follow(String username, CredentialsDto credentialsDto)
			throws InvalidCredentialsException, NotFoundException, InvalidRequestException {
		validations.validateCredentials(credentialsDto);

		Optional<User> optionalCurrentUser = userRepository
				.findByCredentialsUsernameAndActive(credentialsDto.getUsername(), true);
		validations.validateCredentials(credentialsDto, optionalCurrentUser);

		Optional<User> optionalUser = findByActiveUsername(username, "No such followable user exists");

		final User currentUser = optionalCurrentUser.get();
		User user = optionalUser.get();

		if (currentUser.getId().longValue() == user.getId().longValue()) {
			throw new InvalidRequestException("Cannot follow self");
		}
		Set<User> followers = user.getFollowers();

		Optional<User> existingUser = followers.stream()
				.filter(u -> u.getId().longValue() == currentUser.getId().longValue()).findAny();
		if (existingUser.isEmpty()) {
			followers.add(currentUser);
			user = userRepository.saveAndFlush(user);
		} else {
			throw new InvalidRequestException("There is already a following relationship between the two users");
		}

		return new ResponseEntity<UserResponseDto>(HttpStatus.OK);
	}

	public ResponseEntity<?> unfollow(String username, CredentialsDto credentialsDto)
			throws InvalidRequestException, NotFoundException, InvalidCredentialsException {
		validations.validateCredentials(credentialsDto);

		Optional<User> optionalCurrentUser = userRepository
				.findByCredentialsUsernameAndActive(credentialsDto.getUsername(), true);
		validations.validateCredentials(credentialsDto, optionalCurrentUser);

		Optional<User> optionalUser = findByActiveUsername(username, "No such followable user exists");

		final User currentUser = optionalCurrentUser.get();
		User user = optionalUser.get();
		Set<User> followers = user.getFollowers();

		Optional<User> existingUser = followers.stream()
				.filter(u -> u.getId().longValue() == currentUser.getId().longValue()).findAny();
		if (existingUser.isEmpty()) {
			throw new NotFoundException("No such followable user exists");
		}

		followers = followers.stream().filter(u -> u.getId().longValue() != currentUser.getId().longValue())
				.collect(Collectors.toSet());
		user.setFollowers(followers);
		user = userRepository.saveAndFlush(user);

		return new ResponseEntity<UserResponseDto>(HttpStatus.OK);
	}

	private Optional<User> findByActiveUsername(String username, String errorMessage) throws NotFoundException {
		validations.validateUsername(username, errorMessage);
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActive(username, true);
		validations.validateOptionalUser(optionalUser, errorMessage);
		return optionalUser;
	}

	public ResponseEntity<?> getUsersTweets(String username) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActive(username, true);
		validations.validateOptionalUser(optionalUser, "No such user exists");

		List<Tweet> tweets = tweetRepository.getUsersTweets(username);
		Collections.sort(tweets, Comparator.comparing(Tweet::getPosted).reversed());

		return new ResponseEntity<List<TweetDto>>(tweetMapper.entitiesToDtos(tweets), HttpStatus.OK);
	}

	public ResponseEntity<?> getUsersMentions(String username) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActive(username, true);
		validations.validateOptionalUser(optionalUser, "No such user exists");

		List<Tweet> tweets = tweetRepository.findAllByMentionsCredentialsUsername(username);
		Collections.sort(tweets, Comparator.comparing(Tweet::getPosted).reversed());

		return new ResponseEntity<List<TweetDto>>(tweetMapper.entitiesToDtos(tweets), HttpStatus.OK);
	}

	public ResponseEntity<?> getUserFollowers(String username) throws NotFoundException {
		Optional<User> optionalUser = findByActiveUsername(username, "No such user exists");
		return new ResponseEntity<Set<UserResponseDto>>(
				userMapper.entitiesToResponseDtos(optionalUser.get().getFollowers()), HttpStatus.OK);
	}

	public ResponseEntity<?> getUserFollowing(String username) throws NotFoundException {
		Optional<User> optionalUser = findByActiveUsername(username, "No such user exists");

		return new ResponseEntity<Set<UserResponseDto>>(
				userMapper.entitiesToResponseDtos(optionalUser.get().getFollowing()), HttpStatus.OK);
	}
}
