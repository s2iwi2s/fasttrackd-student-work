package com.cooksys.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.backend.dtos.CredentialsDTO;
import com.cooksys.backend.exceptions.InvalidCredentialsException;
import com.cooksys.backend.services.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	private UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO) throws InvalidCredentialsException {
		log.debug("Login username: {}", credentialsDTO.getUsername());
		return userService.login(credentialsDTO);
	}

}
