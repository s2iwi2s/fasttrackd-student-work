package com.cooksys.assessment.socialmedia.common;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.cooksys.assessment.socialmedia.dtos.CreateSimpleTweetDto;
import com.cooksys.assessment.socialmedia.dtos.CredentialsDto;
import com.cooksys.assessment.socialmedia.dtos.UserRequestDto;
import com.cooksys.assessment.socialmedia.entities.Tweet;
import com.cooksys.assessment.socialmedia.entities.User;
import com.cooksys.assessment.socialmedia.exceptions.InvalidCredentialsException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidRequestException;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;

@Component
public class Validations {

	public void validateCredentials(CredentialsDto credentials) throws InvalidCredentialsException {
		if (ObjectUtils.isEmpty(credentials) || !StringUtils.hasLength(credentials.getUsername())) {
			throw new InvalidCredentialsException("Invalid credentials");
		}

		if (!StringUtils.hasLength(credentials.getUsername())) {
			throw new InvalidCredentialsException("Username is required");
		}
		if (!StringUtils.hasLength(credentials.getPassword())) {
			throw new InvalidCredentialsException("Password is required");
		}
	}

	public void validateCredentials(UserRequestDto userDto) throws InvalidCredentialsException {
		if (ObjectUtils.isEmpty(userDto.getCredentials())) {
			throw new InvalidCredentialsException("Invalid credentials.");
		}
		validateCredentials(userDto.getCredentials());
	}

	public void validateProfile(UserRequestDto userDto) throws InvalidRequestException {
		if (ObjectUtils.isEmpty(userDto.getProfile()) || !StringUtils.hasLength(userDto.getProfile().getEmail())) {
			throw new InvalidRequestException("Email is required");
		}
	}

	public void validateOptionalUser(Optional<User> optionalUser, String errorMessage) throws NotFoundException {
		if (optionalUser.isEmpty()) {
			throw new NotFoundException(errorMessage);
		}
	}

	public void validateUsername(String username, String errorMessage) throws NotFoundException {
		if (!StringUtils.hasLength(username)) {
			throw new NotFoundException(errorMessage);
		}
	}

	public void validateCredentials(CredentialsDto credsDto, Optional<User> user) throws InvalidCredentialsException {
		if (user.isEmpty() || !user.get().getCredentials().getPassword().equals(credsDto.getPassword())) {
			throw new InvalidCredentialsException("Invalid credentials.");
		}
	}

	public void validateCreateTweet(CreateSimpleTweetDto createTweetDto) throws InvalidRequestException {
		if (ObjectUtils.isEmpty(createTweetDto)) {
			throw new InvalidRequestException();
		}
		if (!StringUtils.hasLength(createTweetDto.getContent())) {
			throw new InvalidRequestException("Content empty.");
		}
	}

	public void validateTweet(Optional<Tweet> optionalTweet) throws NotFoundException {
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("No such Tweet exists or has been deleted.");
		}
	}
}
