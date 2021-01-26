package com.cooksys.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.quizapp.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
