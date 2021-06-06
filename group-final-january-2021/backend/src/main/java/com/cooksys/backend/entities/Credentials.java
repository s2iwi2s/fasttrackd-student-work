package com.cooksys.backend.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

	@Column(unique = true)
	private String username;
	private String password;
}
