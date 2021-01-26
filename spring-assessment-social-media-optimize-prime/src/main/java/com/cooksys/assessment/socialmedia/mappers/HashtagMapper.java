package com.cooksys.assessment.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.assessment.socialmedia.dtos.HashtagDto;
import com.cooksys.assessment.socialmedia.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);

	HashtagDto entityToDto(Hashtag hashtag);
}
