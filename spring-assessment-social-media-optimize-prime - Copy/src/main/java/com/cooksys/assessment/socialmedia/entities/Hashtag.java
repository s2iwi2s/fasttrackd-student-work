package com.cooksys.assessment.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_id_seq_gen")
	@SequenceGenerator(name = "hashtag_id_seq_gen", sequenceName = "hashtag_id_seq", allocationSize = 1)
	private Long id;

	private String label;

	@CreationTimestamp
	private Timestamp firstUsed;

	@UpdateTimestamp
	private Timestamp lastUsed;

	@ManyToMany(mappedBy = "hashtags")
	private List<Tweet> tweets;
}
