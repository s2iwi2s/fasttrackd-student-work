package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
	private UserDto user;
	private CredentialsDTO credentials;
}
