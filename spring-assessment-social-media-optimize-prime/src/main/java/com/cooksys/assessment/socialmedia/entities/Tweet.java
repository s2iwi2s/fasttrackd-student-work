package com.cooksys.assessment.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tweet {

	public Tweet(User author, Tweet repostOf) {
		super();
		this.author = author;
		this.repostOf = repostOf;
	}

	public Tweet(String content, User author, Tweet inReplyTo) {
		super();
		this.content = content;
		this.author = author;
		this.inReplyTo = inReplyTo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_id_seq_gen")
	@SequenceGenerator(name = "tweet_id_seq_gen", sequenceName = "tweet_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@CreationTimestamp
	private Timestamp posted;

	private String content;

	private boolean deleted;

	@ManyToOne
	private Tweet inReplyTo;

	@ManyToOne
	private Tweet repostOf;

	@ManyToMany
	private Set<User> likes;

	@ManyToMany
	private List<User> mentions;

	@ManyToMany
	private List<Hashtag> hashtags;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((posted == null) ? 0 : posted.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (posted == null) {
			if (other.posted != null)
				return false;
		} else if (!posted.equals(other.posted))
			return false;
		return true;
	}

}
