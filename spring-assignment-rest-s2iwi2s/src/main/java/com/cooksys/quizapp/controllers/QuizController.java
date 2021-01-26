package com.cooksys.quizapp.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.exception.DuplicateQuizException;
import com.cooksys.quizapp.exception.InvalidAnswerRequestException;
import com.cooksys.quizapp.exception.InvalidQestionRequestException;
import com.cooksys.quizapp.exception.InvalidQuizRequestException;
import com.cooksys.quizapp.services.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	private QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping
	public List<QuizDto> list() {
		return quizService.getQuizes();
	}

	@PostMapping
	public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto quizDto) throws InvalidQuizRequestException,
			DuplicateQuizException, InvalidQestionRequestException, InvalidAnswerRequestException {
		return quizService.createQuiz(quizDto);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<QuizDto> deleteQuiz(@PathVariable("id") Long id) {
		return quizService.deleteQuiz(id);
	}

	@PatchMapping(path = "/{id}/rename/{newName}")
	public ResponseEntity<QuizDto> renameQuiz(@PathVariable("id") Long id, @PathVariable("newName") String newName)
			throws InvalidQuizRequestException {
		return quizService.renameQuiz(id, newName);
	}

	@GetMapping(path = "/{id}/random")
	public ResponseEntity<QuestionDto> randomQuestion(@PathVariable("id") Long id) {
		return quizService.randomQuestion(id);
	}

	@PatchMapping(path = "/{id}/add")
	public ResponseEntity<QuizDto> addQuestion(@PathVariable("id") Long id, @RequestBody QuestionDto question)
			throws InvalidQestionRequestException, InvalidAnswerRequestException {
		return quizService.addQuestion(id, question);
	}

	@DeleteMapping(path = "/{id}/delete/{questionID}")
	public ResponseEntity<QuestionDto> deleteQuestion(@PathVariable("id") Long id,
			@PathVariable("questionID") Long questionID) {
		return quizService.deleteQuestion(id, questionID);
	}
}
