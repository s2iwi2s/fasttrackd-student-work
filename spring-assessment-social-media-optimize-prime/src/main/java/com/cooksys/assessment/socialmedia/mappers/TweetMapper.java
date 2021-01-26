package com.cooksys.assessment.socialmedia.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.assessment.socialmedia.dtos.InReplyToDto;
import com.cooksys.assessment.socialmedia.dtos.RepostOfDto;
import com.cooksys.assessment.socialmedia.dtos.TweetDto;
import com.cooksys.assessment.socialmedia.dtos.UserResponseDto;
import com.cooksys.assessment.socialmedia.entities.Tweet;
import com.cooksys.assessment.socialmedia.entities.User;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	@Mapping(source = "author.credentials.username", target = "username")
	List<TweetDto> entitiesToDtos(List<Tweet> tweets);

	@Mapping(source = "author.credentials.username", target = "username")
	List<TweetDto> entitiesToDtos(Set<Tweet> tweets);

	@Mapping(source = "author.credentials.username", target = "username")
	TweetDto entityToDto(Tweet tweet);

	@Mapping(source = "author.credentials.username", target = "username")
	RepostOfDto entityToRepostOfDto(Tweet repostOf);

	@Mapping(source = "author.credentials.username", target = "username")
	InReplyToDto entityToInReplyToDto(Tweet inReplyTo);

	@Mapping(source = "credentials.username", target = "username")
	List<UserResponseDto> entitiesToUserResponseDtos(List<User> users);

	@Mapping(source = "credentials.username", target = "username")
	UserResponseDto entityToUserResponseDto(User user);
}
