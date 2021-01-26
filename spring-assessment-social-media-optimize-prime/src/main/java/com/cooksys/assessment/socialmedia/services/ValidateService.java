package com.cooksys.assessment.socialmedia.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.assessment.socialmedia.entities.Hashtag;
import com.cooksys.assessment.socialmedia.entities.User;
import com.cooksys.assessment.socialmedia.repositories.HashtagRepository;
import com.cooksys.assessment.socialmedia.repositories.UserRepository;

@Service
public class ValidateService {
	private UserRepository userRepo;
	private HashtagRepository hashtagRepo;

	public ValidateService(UserRepository userRepo, HashtagRepository hashtagRepo) {
		this.userRepo = userRepo;
		this.hashtagRepo = hashtagRepo;
	}

	public ResponseEntity<Boolean> doesUsernameExist(String username) {
		Optional<User> user = userRepo.findByCredentialsUsername(username);
		if (user.isPresent())
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Boolean> isUsernameAvailable(String username) {
		Optional<User> user = userRepo.findByCredentialsUsername(username);
		if (user.isPresent())
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	public ResponseEntity<Boolean> doesHashtagExist(String label) {
		Optional<Hashtag> hashtag = hashtagRepo.findByLabel(label);
		if (hashtag.isPresent())
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	}

}
