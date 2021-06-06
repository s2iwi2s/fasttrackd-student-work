package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
	private UserDetailsDto userDetails;
	private CredentialsDTO credentials;
}
