package com.cooksys.backend.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class User {

	public User(Long id, Credentials credentials, Profile profile, Role role) {
		super();
		this.id = id;
		this.credentials = credentials;
		this.profile = profile;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_gen")
	@SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_id_seq", allocationSize = 1)
	private Long id;

	@Embedded
	private Credentials credentials;

	@Embedded
	private Profile profile;

	@ManyToOne
	private Role role;

	@Column(name = "deleted", columnDefinition = "boolean default false")
	private boolean deleted;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

	@OneToOne
	private User updatedBy;

	public void setDeleted() {
		this.deleted = true;
	}

	public void setEnabled() {
		this.deleted = false;
	}
}
