package com.cooksys.quizapp.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizDto {

	private Long id;
	private String name;
	private List<QuestionDto> questions;
}
