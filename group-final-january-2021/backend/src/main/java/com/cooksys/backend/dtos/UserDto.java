package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String username;
	private RoleDto role;
	private ProfileDto profile;
}
