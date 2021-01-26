package com.cooksys.assessment.socialmedia.dtos;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HashtagDto {
	private Long id;
	private String label;
	private Timestamp firstUsed;
	private Timestamp lastUsed;
}
