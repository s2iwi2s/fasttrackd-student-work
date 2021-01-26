package com.cooksys.assessment.socialmedia.dtos;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InReplyToDto {
	private Long id;
	private String username;
	private Timestamp posted;
	private String content;
	private RepostOfDto repostOf;
	List<UserResponseDto> likes;
}
