package com.cooksys.backend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

	public Project(Team team, CommonFields commonFields) {
		this.team = team;
		this.commonFields = commonFields;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq_gen")
	@SequenceGenerator(name = "project_id_seq_gen", sequenceName = "project_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne
	private Team team;

	@Embedded
	private CommonFields commonFields;

	@ManyToMany(mappedBy = "profile.projects")
	private List<User> users;

	@Column(name = "deleted", columnDefinition = "boolean default false")
	private boolean deleted;

	public List<User> addUser(User user) {
		if (users == null) {
			users = new ArrayList<User>();
		}

		users.add(user);
		return users;
	}
}
