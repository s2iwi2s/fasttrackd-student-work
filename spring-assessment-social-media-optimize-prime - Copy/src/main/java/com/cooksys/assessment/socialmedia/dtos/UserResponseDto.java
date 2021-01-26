package com.cooksys.assessment.socialmedia.dtos;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
	private String username;
	private ProfileDto profile;
	private Timestamp joined;
}
