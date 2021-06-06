package com.cooksys.backend.mappers;

import com.cooksys.backend.dtos.ProjectDto;
import com.cooksys.backend.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

	@Mapping(target = "name", source = "commonFields.name")
	@Mapping(target = "text", source = "commonFields.text")
	@Mapping(target = "team.name", source = "team.commonFields.name")
	@Mapping(target = "team.text", source = "team.commonFields.text")
	@Mapping(target = "team.company.name", source = "team.company.commonFields.name")
	@Mapping(target = "team.company.text", source = "team.company.commonFields.text")
	public ProjectDto entityToDto(Project project);

	public List<ProjectDto> entitiesToDtos(List<Project> teams);

	@Mapping(source = "name", target = "commonFields.name")
	@Mapping(source = "text", target = "commonFields.text")
	public Project dtoToEntity(ProjectDto projectDto);

}
