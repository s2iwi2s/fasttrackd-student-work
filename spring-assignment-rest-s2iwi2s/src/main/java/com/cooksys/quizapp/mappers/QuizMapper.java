package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper {

	public List<QuizDto> entitiesToDtos(List<Quiz> quizes);

	public Quiz dtoToEntity(QuizDto quizDto);

	public QuizDto entityToDto(Quiz quiz);
}
