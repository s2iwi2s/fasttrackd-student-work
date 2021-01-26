package com.cooksys.assessment.socialmedia.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetContextDto {
	private TweetDto target;
	private List<TweetDto> before;
	private List<TweetDto> after;

	public TweetContextDto(TweetDto target, List<TweetDto> before, List<TweetDto> after) {
		this.target = target;
		this.before = before;
		this.after = after;
	}

}
