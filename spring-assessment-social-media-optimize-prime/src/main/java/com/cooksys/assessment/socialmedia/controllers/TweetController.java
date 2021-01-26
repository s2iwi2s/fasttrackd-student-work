package com.cooksys.assessment.socialmedia.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment.socialmedia.dtos.CreateSimpleTweetDto;
import com.cooksys.assessment.socialmedia.dtos.CredentialsDto;
import com.cooksys.assessment.socialmedia.dtos.HashtagDto;
import com.cooksys.assessment.socialmedia.dtos.TweetContextDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.dtos.UserResponseDto;
import com.cooksys.assessment.socialmedia.exceptions.DuplicateException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidCredentialsException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidRequestException;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.services.TweetService;

@RestController
@RequestMapping("/tweets")
public class TweetController {

	private TweetService tweetService;

	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@GetMapping()
	public ResponseEntity<List<TweetDto>> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@PostMapping()
	public ResponseEntity<TweetDto> createSimpleTweet(@RequestBody CreateSimpleTweetDto createSimpleTweetDto)
			throws InvalidCredentialsException, InvalidRequestException {
		return tweetService.createSimpleTweet(createSimpleTweetDto);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<TweetDto> getTweet(@PathVariable("id") Long tweetId) throws NotFoundException {
		return tweetService.getTweet(tweetId);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TweetDto> deleteTweet(@RequestBody CredentialsDto credsDto, @PathVariable("id") Long tweetId)
			throws InvalidCredentialsException, NotFoundException {
		return tweetService.deleteTweet(tweetId, credsDto);
	}

	@PostMapping(path = "/{id}/like")
	public ResponseEntity<?> likeTweet(@RequestBody CredentialsDto credsDto, @PathVariable("id") Long tweetId)
			throws InvalidCredentialsException, NotFoundException, DuplicateException {
		return tweetService.likeTweet(tweetId, credsDto);
	}

	@PostMapping(path = "/{id}/reply")
	public ResponseEntity<TweetDto> replyToTweet(@RequestBody CreateSimpleTweetDto createTweetDto,
			@PathVariable("id") Long tweetId)
			throws InvalidRequestException, InvalidCredentialsException, NotFoundException {
		return tweetService.replyToTweet(tweetId, createTweetDto);
	}

	@PostMapping(path = "/{id}/repost")
	public ResponseEntity<TweetDto> repostTweet(@RequestBody CredentialsDto credentials,
			@PathVariable("id") Long tweetId) throws InvalidCredentialsException, NotFoundException {
		return tweetService.repostTweet(tweetId, credentials);
	}

	@GetMapping(path = "/{id}/tags")
	public ResponseEntity<List<HashtagDto>> getTweetTags(@PathVariable("id") Long tweetId) throws NotFoundException {
		return tweetService.getTweetTags(tweetId);
	}

	@GetMapping(path = "/{id}/likes")
	public ResponseEntity<Set<UserResponseDto>> getTweetLikes(@PathVariable("id") Long tweetId)
			throws NotFoundException {
		return tweetService.getTweetLikes(tweetId);
	}

	@GetMapping(path = "/{id}/context")
	public ResponseEntity<TweetContextDto> getTweetContext(@PathVariable("id") Long tweetId) throws NotFoundException {
		return tweetService.getTweetContext(tweetId);
	}

	@GetMapping(path = "/{id}/replies")
	public ResponseEntity<List<TweetDto>> getTweetReplies(@PathVariable("id") Long tweetId) throws NotFoundException {
		return tweetService.getTweetReplies(tweetId);
	}

	@GetMapping(path = "/{id}/reposts")
	public ResponseEntity<List<TweetDto>> getTweetReposts(@PathVariable("id") Long tweetId) throws NotFoundException {
		return tweetService.getTweetReposts(tweetId);
	}

	@GetMapping(path = "/{id}/mentions")
	public ResponseEntity<Set<UserResponseDto>> getTweetMentions(@PathVariable("id") Long tweetId)
			throws NotFoundException {
		return tweetService.getTweetMentions(tweetId);
	}
}
