package com.cooksys.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.dtos.UserRequestDto;
import com.cooksys.backend.exceptions.DuplicateException;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.exceptions.InvalidRequestException;
import com.cooksys.backend.exceptions.NotFoundException;
import com.cooksys.backend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody UserRequestDto userRequestDto)
			throws InvalidRequestException, DuplicateException, InvalidCredentialsException, NotFoundException {
		return userService.save(userRequestDto);
	}

	@PutMapping("/{username}")
	public ResponseEntity<?> update(@PathVariable("username") String username,
			@RequestBody UserRequestDto userRequestDto)
			throws InvalidCredentialsException, InvalidRequestException, DuplicateException, NotFoundException {
		return userService.update(username, userRequestDto);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<?> delete(@PathVariable("username") String username, @RequestBody CredentialsDTO credentials)
			throws NotFoundException, InvalidCredentialsException, InvalidRequestException {
		return userService.delete(username, credentials);
	}
}
