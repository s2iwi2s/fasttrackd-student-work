package com.cooksys.backend.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Company {

	public Company(CommonFields commonFields) {
		this.commonFields = commonFields;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_seq_gen")
	@SequenceGenerator(name = "company_id_seq_gen", sequenceName = "company_id_seq", allocationSize = 1)
	private Long id;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<Team> teams;

	@OneToMany(mappedBy = "profile.company", cascade = CascadeType.ALL)
	private List<User> users;

	@Embedded
	private CommonFields commonFields;
}
