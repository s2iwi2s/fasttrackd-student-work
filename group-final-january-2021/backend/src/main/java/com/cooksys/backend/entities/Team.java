package com.cooksys.backend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Team {

	public Team(Company company, CommonFields commonFields) {
		this.company = company;
		this.commonFields = commonFields;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq_gen")
	@SequenceGenerator(name = "team_id_seq_gen", sequenceName = "team_id_seq", allocationSize = 1)
	private Long id;

	@OneToMany(mappedBy = "profile.team", cascade = CascadeType.ALL)
	private List<User> users;

	@ManyToOne
	private Company company;

	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	private List<Project> projects;

	@Embedded
	private CommonFields commonFields;

	@Column(name = "deleted", columnDefinition = "boolean default false")
	private boolean deleted;

}
