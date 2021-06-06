package com.cooksys.backend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.backend.dtos.TeamDto;
import com.cooksys.backend.entities.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {
	@Mapping(target = "name", source = "commonFields.name")
	@Mapping(target = "text", source = "commonFields.text")
	@Mapping(target = "company.name", source = "company.commonFields.name")
	@Mapping(target = "company.text", source = "company.commonFields.text")
	public TeamDto entityToDto(Team team);

	public List<TeamDto> entitiesToDtos(List<Team> teams);

	@Mapping(source = "name", target = "commonFields.name")
	@Mapping(source = "text", target = "commonFields.text")
	public Team dtoToEntity(TeamDto teamDto);
}
