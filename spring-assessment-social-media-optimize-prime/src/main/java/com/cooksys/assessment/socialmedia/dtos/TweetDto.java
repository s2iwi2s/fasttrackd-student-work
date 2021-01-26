package com.cooksys.assessment.socialmedia.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TweetDto {
	private Long id;
	private String username;
	private Timestamp posted;
	private String content;
	private InReplyToDto inReplyTo;
	private RepostOfDto repostOf;
	private List<UserResponseDto> likes;
	private List<UserResponseDto> mentions;
	private List<HashtagDto> hashtags;
}
