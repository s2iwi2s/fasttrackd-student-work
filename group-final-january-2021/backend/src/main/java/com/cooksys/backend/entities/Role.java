package com.cooksys.backend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq_gen")
	@SequenceGenerator(name = "role_id_seq_gen", sequenceName = "role_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	private String description;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<User> users;

	public List<User> addUser(User user) {
		if (users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);

		return users;
	}
}
