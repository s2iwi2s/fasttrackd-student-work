package com.cooksys.assessment.socialmedia.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment.socialmedia.services.ValidateService;

@RestController
@RequestMapping("/validate")
public class ValidateController {

	private ValidateService validateService;

	public ValidateController(ValidateService validateService) {
		this.validateService = validateService;
	}

	@GetMapping(path = "/tag/exists/{label}")
	public ResponseEntity<Boolean> doesHashtagExist(@PathVariable String label) {
		return validateService.doesHashtagExist(label);
	}

	@GetMapping(path = "/username/exists/{username}")
	public ResponseEntity<Boolean> doesUsernameExist(@PathVariable String username) {
		return validateService.doesUsernameExist(username);
	}

	@GetMapping(path = "/username/available/{username}")
	public ResponseEntity<Boolean> isUsernameAvailable(@PathVariable String username) {
		return validateService.isUsernameAvailable(username);
	}
}
