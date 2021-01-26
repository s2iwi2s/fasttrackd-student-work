package com.cooksys.quizapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cooksys.quizapp.entities.Answer;
import com.cooksys.quizapp.entities.Question;
import com.cooksys.quizapp.entities.Quiz;
import com.cooksys.quizapp.repositories.AnswerRepository;
import com.cooksys.quizapp.repositories.QuestionRepository;
import com.cooksys.quizapp.repositories.QuizRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(QuizRepository quizRepository, QuestionRepository questionRepository,
			AnswerRepository answerRepository) {
		return (args) -> {
			Quiz quiz1 = new Quiz(null, "Database Quiz", null);
			quiz1 = quizRepository.saveAndFlush(quiz1);

			Quiz quiz2 = new Quiz(null, "Java Quiz", null);
			quizRepository.saveAndFlush(quiz2);

			for (int i = 0; i < 10; i++) {
				Answer a = new Answer(null, "a " + i, null);
				answerRepository.saveAndFlush(a);
				Quiz quiz = i % 3 == 0 ? quiz1 : quiz2;
				Question q = new Question(null, "q " + i, quiz, a);
				questionRepository.saveAndFlush(q);

				a.setQuestion(q);
				answerRepository.saveAndFlush(a);
			}
		};
	}
}
