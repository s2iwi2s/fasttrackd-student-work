package com.cooksys.assessment.socialmedia.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.assessment.socialmedia.common.Validations;
import com.cooksys.assessment.socialmedia.dtos.CreateSimpleTweetDto;
import com.cooksys.assessment.socialmedia.dtos.CredentialsDto;
import com.cooksys.assessment.socialmedia.dtos.HashtagDto;
import com.cooksys.assessment.socialmedia.dtos.TweetContextDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.dtos.UserResponseDto;
import com.cooksys.assessment.socialmedia.entities.Hashtag;
import com.cooksys.assessment.socialmedia.entities.Tweet;
import com.cooksys.assessment.socialmedia.entities.User;
import com.cooksys.assessment.socialmedia.exceptions.DuplicateException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidCredentialsException;
import com.cooksys.assessment.socialmedia.exceptions.InvalidRequestException;
import com.cooksys.assessment.socialmedia.exceptions.NotFoundException;
import com.cooksys.assessment.socialmedia.mappers.HashtagMapper;
import com.cooksys.assessment.socialmedia.mappers.TweetMapper;
import com.cooksys.assessment.socialmedia.mappers.UserMapper;
import com.cooksys.assessment.socialmedia.repositories.HashtagRepository;
import com.cooksys.assessment.socialmedia.repositories.TweetRepository;
import com.cooksys.assessment.socialmedia.repositories.UserRepository;

@Service
public class TweetService {

	TweetRepository tweetRepo;
	UserRepository userRepo;
	HashtagRepository hashtagRepo;
	TweetMapper tweetMapper;
	UserMapper userMapper;
	HashtagMapper hashtagMapper;
	Validations validations;

	public TweetService(TweetRepository tweetRepo, TweetMapper tweetMapper, HashtagMapper hashtagMapper,
			UserRepository userRepo, UserMapper userMapper, HashtagRepository hashtagRepo, Validations validations) {
		this.tweetRepo = tweetRepo;
		this.tweetMapper = tweetMapper;
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.hashtagRepo = hashtagRepo;
		this.validations = validations;
		this.hashtagMapper = hashtagMapper;
	}

	public ResponseEntity<List<TweetDto>> getAllTweets() {
		List<TweetDto> tweets = tweetMapper.entitiesToDtos(tweetRepo.findAllByDeletedFalseOrderByPostedDesc());
		return new ResponseEntity<List<TweetDto>>(tweets, HttpStatus.OK);
	}

	public ResponseEntity<TweetDto> createSimpleTweet(CreateSimpleTweetDto createSimpleTweetDto)
			throws InvalidCredentialsException, InvalidRequestException {
		validations.validateCreateTweet(createSimpleTweetDto);

		Optional<User> user = userRepo
				.findByCredentialsUsernameAndActive(createSimpleTweetDto.getCredentials().getUsername(), true);
		validations.validateCredentials(createSimpleTweetDto.getCredentials(), user);

		Tweet tweet = new Tweet();
		tweet.setAuthor(user.get());
		tweet.setContent(createSimpleTweetDto.getContent());
		tweet.setMentions(parseForMentions(tweet.getContent()));
		tweet.setHashtags(parseForHashtags(tweet.getContent()));
		tweet = tweetRepo.saveAndFlush(tweet);
		tweet.setHashtags(updateHashtagLastUsed(tweet.getHashtags(), tweet.getPosted()));
		return new ResponseEntity<TweetDto>(tweetMapper.entityToDto(tweet), HttpStatus.OK);
	}

	private List<User> parseForMentions(String content) throws InvalidRequestException {
		List<String> splitContent = new ArrayList<>(Arrays.asList(content.split("@")));
		List<User> mentions = new ArrayList<>();

		splitContent.remove(0);

		for (String mention : splitContent) {
			mentions.add(parseMention(mention));
		}
		return mentions;
	}

	private User parseMention(String mention) throws InvalidRequestException {
		int endOfMention = calcEndOfMention(mention);

		Optional<User> mentionUser = userRepo.findByCredentialsUsernameAndActive(mention.substring(0, endOfMention),
				true);
		if (mentionUser.isEmpty())
			throw new InvalidRequestException("Mention @" + mention.substring(0, endOfMention) + " does not exist.");
		return mentionUser.get();
	}

	private int calcEndOfMention(String mention) {
		int indexOfSpace = mention.indexOf(" ");
		int indexOfHash = mention.indexOf("#");

		if (indexOfSpace == -1 && indexOfHash == -1)
			return mention.length();
		if (indexOfSpace == -1)
			return indexOfHash;
		if (indexOfHash == -1)
			return indexOfSpace;
		return (indexOfSpace < indexOfHash) ? indexOfSpace : indexOfHash;
	}

	private List<Hashtag> parseForHashtags(String content) throws InvalidRequestException {
		List<String> splitContent = new ArrayList<>(Arrays.asList(content.split("#")));
		List<Hashtag> hashtags = new ArrayList<>();

		splitContent.remove(0);

		for (String hashtag : splitContent) {
			hashtags.add(parseHashtag(hashtag));
		}
		return hashtags;
	}

	private Hashtag parseHashtag(String hashtagLabel) throws InvalidRequestException {
		int endOfHashtag = calcEndOfHashtag(hashtagLabel);

		hashtagLabel = hashtagLabel.substring(0, endOfHashtag);

		Optional<Hashtag> optHashtag = hashtagRepo.findByLabel(hashtagLabel);
		if (optHashtag.isPresent())
			return optHashtag.get();
		return createHashtag(hashtagLabel);
	}

	private int calcEndOfHashtag(String hashtagLabel) {
		int indexOfSpace = hashtagLabel.indexOf(" ");
		int indexOfAt = hashtagLabel.indexOf("@");

		if (indexOfSpace == -1 && indexOfAt == -1)
			return hashtagLabel.length();
		if (indexOfSpace == -1)
			return indexOfAt;
		if (indexOfAt == -1)
			return indexOfSpace;
		return (indexOfSpace < indexOfAt) ? indexOfSpace : indexOfAt;
	}

	private Hashtag createHashtag(String label) {
		Hashtag newHashtag = new Hashtag();
		newHashtag.setLabel(label);
		return hashtagRepo.saveAndFlush(newHashtag);
	}

	private List<Hashtag> updateHashtagLastUsed(List<Hashtag> hashtags, Timestamp lastUsed) {
		for (Hashtag hashtag : hashtags) {
			hashtag.setLastUsed(lastUsed);
		}
		hashtagRepo.saveAll(hashtags);
		return hashtags;
	}

	public ResponseEntity<TweetDto> getTweet(Long tweetId) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		if (tweet.isEmpty())
			throw new NotFoundException("No such Tweet exists or has been deleted.");
		return new ResponseEntity<TweetDto>(tweetMapper.entityToDto(tweet.get()), HttpStatus.OK);
	}

	public ResponseEntity<TweetDto> deleteTweet(Long tweetId, CredentialsDto credsDto)
			throws InvalidCredentialsException, NotFoundException {
		Optional<User> user = userRepo.findByCredentialsUsernameAndActive(credsDto.getUsername(), true);
		validations.validateCredentials(credsDto, user);

		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);

		if (tweet.isEmpty())
			throw new NotFoundException("Tweet does not exist or is already deleted.");
		if (!tweet.get().getAuthor().equals(user.get()))
			throw new InvalidCredentialsException("Creds do not match Tweet author.");

		tweet.get().setDeleted(true);
		tweetRepo.saveAndFlush(tweet.get());

		return new ResponseEntity<TweetDto>(tweetMapper.entityToDto(tweet.get()), HttpStatus.OK);
	}

	public ResponseEntity<?> likeTweet(Long tweetId, CredentialsDto credsDto)
			throws InvalidCredentialsException, NotFoundException, DuplicateException {
		Optional<User> user = userRepo.findByCredentialsUsernameAndActive(credsDto.getUsername(), true);
		validations.validateCredentials(credsDto, user);

		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		if (tweet.isEmpty())
			throw new NotFoundException("Tweet does not exist or has been deleted.");

		Set<User> likes = tweet.get().getLikes();
		if (likes.contains(user.get()))
			throw new DuplicateException("User already liked Tweet.");
		likes.add(user.get());
		tweet.get().setLikes(likes);

		tweetRepo.saveAndFlush(tweet.get());

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public ResponseEntity<TweetDto> replyToTweet(Long tweetId, CreateSimpleTweetDto createTweetDto)
			throws InvalidRequestException, InvalidCredentialsException, NotFoundException {
		validations.validateCreateTweet(createTweetDto);
		validations.validateCredentials(createTweetDto.getCredentials());

		Optional<User> user = userRepo.findByCredentialsUsernameAndActive(createTweetDto.getCredentials().getUsername(),
				true);
		validations.validateCredentials(createTweetDto.getCredentials(), user);

		Optional<Tweet> optionalTweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(optionalTweet);

		Tweet inReplyTo = new Tweet(createTweetDto.getContent(), user.get(), optionalTweet.get());
		inReplyTo = tweetRepo.saveAndFlush(inReplyTo);

		return new ResponseEntity<TweetDto>(tweetMapper.entityToDto(inReplyTo), HttpStatus.OK);
	}

	public ResponseEntity<TweetDto> repostTweet(Long tweetId, CredentialsDto credsDto)
			throws InvalidCredentialsException, NotFoundException {
		validations.validateCredentials(credsDto);

		Optional<User> user = userRepo.findByCredentialsUsernameAndActive(credsDto.getUsername(), true);
		validations.validateCredentials(credsDto, user);

		Optional<Tweet> optionalTweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(optionalTweet);

		Tweet repostTweet = new Tweet(user.get(), optionalTweet.get());

		repostTweet = tweetRepo.saveAndFlush(repostTweet);
		return new ResponseEntity<TweetDto>(tweetMapper.entityToDto(repostTweet), HttpStatus.OK);
	}

	public ResponseEntity<List<HashtagDto>> getTweetTags(Long tweetId) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		if (tweet.isEmpty())
			throw new NotFoundException("Tweet does not exist or has been deleted.");

		List<Hashtag> hashtags = tweet.get().getHashtags();

		return new ResponseEntity<List<HashtagDto>>(hashtagMapper.entitiesToDtos(hashtags), HttpStatus.OK);
	}

	public ResponseEntity<Set<UserResponseDto>> getTweetLikes(Long tweetId) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		if (tweet.isEmpty())
			throw new NotFoundException("Tweet does not exist or has been deleted.");

		Set<User> users = tweet.get().getLikes();
		Set<User> activeUsers = new HashSet<>();
		for (User user : users) {
			if (user.isActive() == true)
				activeUsers.add(user);
		}
		return new ResponseEntity<Set<UserResponseDto>>(userMapper.entitiesToResponseDtos(activeUsers), HttpStatus.OK);
	}

	public ResponseEntity<TweetContextDto> getTweetContext(Long tweetId) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(tweet);

		TweetDto target = tweetMapper.entityToDto(tweet.get());
		SortedSet<Tweet> beforeList = new TreeSet<>(Comparator.comparing(Tweet::getPosted));
		setBeforeInReplyToList(tweet.get().getInReplyTo(), beforeList);
		List<TweetDto> before = tweetMapper.entitiesToDtos(beforeList);

		HashSet<Tweet> afterListTemp = new HashSet<>();
		setAfterInReplyToList(tweet.get(), afterListTemp);

		SortedSet<Tweet> afterList = new TreeSet<>(Comparator.comparing(Tweet::getPosted));
		afterList.addAll(afterListTemp);

		List<TweetDto> after = tweetMapper.entitiesToDtos(afterList);
		TweetContextDto contextDto = new TweetContextDto(target, before, after);

		return new ResponseEntity<TweetContextDto>(contextDto, HttpStatus.OK);
	}

	private Set<Tweet> setBeforeInReplyToList(Tweet tweet, Set<Tweet> inReplyToList) {
		if (tweet != null) {
			if (!inReplyToList.contains(tweet)) {
				if (!tweet.isDeleted()) {
					inReplyToList.add(tweet);
				}
				setBeforeInReplyToList(tweet.getInReplyTo(), inReplyToList);
			}

		}
		return inReplyToList;
	}

	private Set<Tweet> setAfterInReplyToList(Tweet tweet, HashSet<Tweet> inReplyToList) {
		Set<Tweet> list = tweetRepo.findByInReplyToOrderByPostedDesc(tweet);
		for (Tweet t : list) {
			if (t != null) {
				if (!inReplyToList.contains(tweet)) {
					if (!t.isDeleted()) {
						inReplyToList.add(t);
					}
					setAfterInReplyToList(t, inReplyToList);
				}
			}
		}

		return inReplyToList;
	}

	public ResponseEntity<List<TweetDto>> getTweetReplies(Long tweetId) throws NotFoundException {
		Optional<Tweet> inReplyTo = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(inReplyTo);

		List<Tweet> list = tweetRepo.findAllByInReplyToAndDeletedFalseOrderByPostedDesc(inReplyTo.get());
		return new ResponseEntity<List<TweetDto>>(tweetMapper.entitiesToDtos(list), HttpStatus.OK);
	}

	public ResponseEntity<List<TweetDto>> getTweetReposts(Long tweetId) throws NotFoundException {
		Optional<Tweet> repostOf = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(repostOf);

		List<Tweet> list = tweetRepo.findAllByRepostOfAndDeletedFalse(repostOf.get());
		return new ResponseEntity<List<TweetDto>>(tweetMapper.entitiesToDtos(list), HttpStatus.OK);
	}

	public ResponseEntity<Set<UserResponseDto>> getTweetMentions(Long tweetId) throws NotFoundException {
		Optional<Tweet> tweet = tweetRepo.findByDeletedFalseAndId(tweetId);
		validations.validateTweet(tweet);
		List<User> mentions = tweet.get().getMentions();
		Set<User> list = mentions.stream().filter(t -> t.isActive()).collect(Collectors.toSet());
		return new ResponseEntity<Set<UserResponseDto>>(userMapper.entitiesToResponseDtos(list), HttpStatus.OK);
	}
}
