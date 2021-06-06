package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectDto {
	private String name;
	private String text;
	private TeamDto team;
}
