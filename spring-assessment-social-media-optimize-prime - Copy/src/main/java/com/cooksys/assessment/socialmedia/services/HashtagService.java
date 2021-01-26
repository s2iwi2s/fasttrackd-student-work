package com.cooksys.assessment.socialmedia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.assessment.socialmedia.dtos.HashtagDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.entities.Hashtag;
import com.cooksys.assessment.socialmedia.entities.Tweet;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.mappers.HashtagMapper;
import com.cooksys.assessment.socialmedia.mappers.TweetMapper;
import com.cooksys.assessment.socialmedia.repositories.HashtagRepository;
import com.cooksys.assessment.socialmedia.repositories.TweetRepository;

@Service
public class HashtagService {
	private HashtagRepository hashtagRepo;
	private TweetRepository tweetRepo;
	private HashtagMapper hashtagMapper;
	private TweetMapper tweetMapper;

	public HashtagService(HashtagRepository hashtagRepo, HashtagMapper hashtagMapper, TweetRepository tweetRepo,
			TweetMapper tweetMapper) {
		this.hashtagRepo = hashtagRepo;
		this.hashtagMapper = hashtagMapper;
		this.tweetRepo = tweetRepo;
		this.tweetMapper = tweetMapper;
	}

	public ResponseEntity<List<HashtagDto>> getAllTags() {
		List<Hashtag> hashtags = hashtagRepo.findAll();
		return new ResponseEntity<List<HashtagDto>>(hashtagMapper.entitiesToDtos(hashtags), HttpStatus.OK);
	}

	public ResponseEntity<List<TweetDto>> getTweetsByHashtagLabel(String label) throws NotFoundException {
		Optional<Hashtag> hashtag = hashtagRepo.findByLabel(label);
		if (hashtag.isEmpty())
			throw new NotFoundException("Hashtag does not exist.");

		Optional<List<Tweet>> tweets = tweetRepo.findAllByHashtagsLabelAndDeletedFalseOrderByPostedDesc(label);
		if (tweets.isEmpty())
			throw new NotFoundException("No Tweets found.");

		return new ResponseEntity<List<TweetDto>>(tweetMapper.entitiesToDtos(tweets.get()), HttpStatus.OK);
	}

}
