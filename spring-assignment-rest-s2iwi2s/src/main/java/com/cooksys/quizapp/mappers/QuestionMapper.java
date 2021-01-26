package com.cooksys.quizapp.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

	public List<QuizDto> entitiesToDtos(List<Question> quizes);

	public Question dtoToEntity(QuestionDto quizDto);

	public QuestionDto entityToDto(Question question);
}
