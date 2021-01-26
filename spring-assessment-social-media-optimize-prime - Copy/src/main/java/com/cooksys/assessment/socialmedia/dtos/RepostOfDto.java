package com.cooksys.assessment.socialmedia.dtos;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RepostOfDto {
	private Long id;
	private String username;
	private Timestamp posted;
	private String content;
}
