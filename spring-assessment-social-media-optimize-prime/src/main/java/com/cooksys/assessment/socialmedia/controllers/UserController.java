package com.cooksys.assessment.socialmedia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment.socialmedia.dtos.CredentialsDto;
import com.cooksys.assessment.socialmedia.dtos.UserRequestDto;
import com.cooksys.assessment.socialmedia.exceptions.DuplicateException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidCredentialsException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidRequestException;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<?> getUsers() {
		return userService.getUsers();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody UserRequestDto userRequestDto)
			throws InvalidCredentialsException, InvalidRequestException, DuplicateException {
		return userService.create(userRequestDto);
	}

	@GetMapping(path = "/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) throws NotFoundException {
		return userService.getUser(username);
	}

	@PatchMapping(path = "/{username}")
	public ResponseEntity<?> update(@PathVariable("username") String username,
			@RequestBody UserRequestDto userRequestDto)
			throws InvalidCredentialsException, InvalidRequestException, NotFoundException, DuplicateException {
		return userService.update(username, userRequestDto);
	}

	@DeleteMapping(path = "/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username,
			@RequestBody CredentialsDto credentials) throws InvalidCredentialsException, NotFoundException {
		return userService.deleteUser(username, credentials);
	}

	@PostMapping(path = "/{username}/follow")
	public ResponseEntity<?> follow(@PathVariable("username") String username,
			@RequestBody CredentialsDto credentialsDto)
			throws InvalidCredentialsException, NotFoundException, InvalidRequestException {
		log.debug("follow username={}, credentialsDto={}", username, credentialsDto);
		return userService.follow(username, credentialsDto);
	}

	@PostMapping(path = "/{username}/unfollow")
	public ResponseEntity<?> unfollow(@PathVariable("username") String username,
			@RequestBody CredentialsDto credentialsDto)
			throws InvalidRequestException, NotFoundException, InvalidCredentialsException {
		return userService.unfollow(username, credentialsDto);
	}

	@GetMapping(path = "/{username}/tweets")
	public ResponseEntity<?> usersTweets(@PathVariable("username") String username) throws NotFoundException {
		return userService.getUsersTweets(username);
	}

	@GetMapping(path = "/{username}/mentions")
	public ResponseEntity<?> usersMentions(@PathVariable("username") String username) throws NotFoundException {
		return userService.getUsersMentions(username);
	}

	@GetMapping(path = "/{username}/followers")
	public ResponseEntity<?> userFollowers(@PathVariable("username") String username) throws NotFoundException {
		return userService.getUserFollowers(username);
	}

	@GetMapping(path = "/{username}/following")
	public ResponseEntity<?> userFollowing(@PathVariable("username") String username) throws NotFoundException {
		return userService.getUserFollowing(username);
	}
}
