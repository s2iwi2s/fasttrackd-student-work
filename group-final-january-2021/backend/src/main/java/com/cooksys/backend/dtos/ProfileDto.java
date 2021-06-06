package com.cooksys.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProfileDto {

	private String firstName;
	private String lastName;

	private CompanyDto company;
	private TeamDto team;
	private ProjectDto project;

}
