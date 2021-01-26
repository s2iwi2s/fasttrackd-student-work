package com.cooksys.assessment.socialmedia.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment.socialmedia.dtos.HashtagDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.services.HashtagService;

@RestController
@RequestMapping("/tags")
public class HashtagController {

	private HashtagService hashtagService;

	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}

	@GetMapping()
	public ResponseEntity<List<HashtagDto>> getAllTags() {
		return hashtagService.getAllTags();
	}

	@GetMapping(path = "/{label}")
	public ResponseEntity<List<TweetDto>> getTweetsByHashtagLabel(@PathVariable String label) throws NotFoundException {
		return hashtagService.getTweetsByHashtagLabel(label);
	}
}
