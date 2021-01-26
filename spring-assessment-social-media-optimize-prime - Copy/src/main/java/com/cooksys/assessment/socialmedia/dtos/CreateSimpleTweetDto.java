package com.cooksys.assessment.socialmedia.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateSimpleTweetDto {
	private String content;
	private CredentialsDto credentials;
}
