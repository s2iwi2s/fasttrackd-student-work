package com.cooksys.backend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Profile {

	private String firstName;
	private String lastName;

	@ManyToOne
	private Team team;

	@ManyToOne
	private Company company;

	@ManyToMany
	private List<Project> projects;

	public List<Project> addUser(Project project) {
		if (projects == null) {
			projects = new ArrayList<Project>();
		}
		projects.add(project);
		return projects;
	}
}
