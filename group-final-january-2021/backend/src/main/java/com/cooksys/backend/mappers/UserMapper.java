package com.cooksys.backend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.backend.dtos.UserDto;
import com.cooksys.backend.dtos.UserRequestDto;
import com.cooksys.backend.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(source = "credentials.username", target = "username")
	@Mapping(source = "profile.company.commonFields.name", target = "profile.company.name")
	@Mapping(source = "profile.team.commonFields.name", target = "profile.team.name")
	@Mapping(source = "profile.team.commonFields.text", target = "profile.team.text")
	public UserDto entityToDto(User user);

	@Mapping(source = "username", target = "credentials.username")
	public User dtoToEntity(UserDto userDto);

	@Mapping(source = "userDetails.credentials", target = "credentials")
	@Mapping(source = "userDetails.user.profile.firstName", target = "profile.firstName")
	@Mapping(source = "userDetails.user.profile.lastName", target = "profile.lastName")
	@Mapping(source = "userDetails.user.profile.company.name", target = "profile.company.commonFields.name")
	@Mapping(source = "userDetails.user.profile.team.name", target = "profile.team.commonFields.name")
	public User requestDtoToEntity(UserRequestDto userDto);

	public List<UserDto> entitiesToDtos(List<User> users);
}
