package com.cooksys.assessment.socialmedia.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.assessment.socialmedia.dtos.ProfileDto;
import com.cooksys.assessment.socialmedia.dtos.UserRequestDto;
import com.cooksys.assessment.socialmedia.dtos.UserResponseDto;
import com.cooksys.assessment.socialmedia.entities.Profile;
import com.cooksys.assessment.socialmedia.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	public User requestDtoToEntity(UserRequestDto userDto);

	@Mapping(source = "credentials.username", target = "username")
	public UserResponseDto entityToResponsetDto(User user);

	@Mapping(source = "credentials.username", target = "username")
	public Set<UserResponseDto> entitiesToResponseDtos(Set<User> users);

	public Profile profiletoToEntity(ProfileDto userDto);

}
