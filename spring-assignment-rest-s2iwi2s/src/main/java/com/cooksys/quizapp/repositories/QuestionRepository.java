package com.cooksys.quizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.quizapp.entities.Question;
import com.cooksys.quizapp.entities.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByQuiz(Quiz quiz);
}
