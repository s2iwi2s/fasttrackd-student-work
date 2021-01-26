package com.cooksys.quizapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.quizapp.dtos.AnswerDto;
import com.cooksys.quizapp.dtos.QuestionDto;
import com.cooksys.quizapp.dtos.QuizDto;
import com.cooksys.quizapp.entities.Answer;
import com.cooksys.quizapp.entities.Question;
import com.cooksys.quizapp.entities.Quiz;
import com.cooksys.quizapp.exception.DuplicateQuizException;
import com.cooksys.quizapp.exception.InvalidAnswerRequestException;
import com.cooksys.quizapp.exception.InvalidQestionRequestException;
import com.cooksys.quizapp.exception.InvalidQuizRequestException;
import com.cooksys.quizapp.mappers.QuestionMapper;
import com.cooksys.quizapp.mappers.QuizMapper;
import com.cooksys.quizapp.repositories.AnswerRepository;
import com.cooksys.quizapp.repositories.QuestionRepository;
import com.cooksys.quizapp.repositories.QuizRepository;

@Service
public class QuizService {

	private QuizRepository quizRepository;
	private QuizMapper quizMapper;
	private QuestionRepository questionRepository;
	private QuestionMapper questionMapper;
	private AnswerRepository answerRepository;

	public QuizService(QuizRepository quizRepository, QuizMapper quizMapper, QuestionRepository questionRepository,
			QuestionMapper questionMapper, AnswerRepository answerRepository) {
		this.quizRepository = quizRepository;
		this.quizMapper = quizMapper;
		this.questionRepository = questionRepository;
		this.questionMapper = questionMapper;
		this.answerRepository = answerRepository;
	}

	public List<QuizDto> getQuizes() {
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	public ResponseEntity<QuizDto> createQuiz(QuizDto quizDto) throws InvalidQuizRequestException,
			DuplicateQuizException, InvalidQestionRequestException, InvalidAnswerRequestException {

		validateQuizName(quizDto.getName());

		List<Quiz> quizes = quizRepository.findByName(quizDto.getName());
		if (quizes.size() > 0) {
			throw new DuplicateQuizException();
		}

		// if provided, validate question and answer
		if (quizDto.getQuestions() != null && quizDto.getQuestions().size() > 0) {
			for (QuestionDto questionDto : quizDto.getQuestions()) {
				validateQuestion(questionDto);
				validateAnswer(questionDto.getAnswer());
			}
		}

		Quiz quiz = quizMapper.dtoToEntity(quizDto);
		quiz = quizRepository.saveAndFlush(quiz);

		List<Question> questions = new ArrayList<Question>();
		if (quiz.getQuestions() != null && quiz.getQuestions().size() > 0) {
			for (Question q : quiz.getQuestions()) {
				if (q.getAnswer() != null) {
					Answer answer = answerRepository.save(q.getAnswer());
					q.setAnswer(answer);
				}
				q.setQuiz(quiz);
				Question question = questionRepository.saveAndFlush(q);
				questions.add(question);
			}
		}
		quiz.setQuestions(questions);
		return new ResponseEntity<QuizDto>(quizMapper.entityToDto(quiz), HttpStatus.CREATED);
	}

	public ResponseEntity<QuizDto> deleteQuiz(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			return new ResponseEntity<QuizDto>(HttpStatus.NOT_FOUND);
		}
		Quiz quiz = optionalQuiz.get();
		quizRepository.delete(quiz);
		return new ResponseEntity<QuizDto>(quizMapper.entityToDto(quiz), HttpStatus.OK);
	}

	public ResponseEntity<QuizDto> renameQuiz(Long id, String newName) throws InvalidQuizRequestException {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			return new ResponseEntity<QuizDto>(HttpStatus.NOT_FOUND);
		}

		validateQuizName(newName);

		Quiz quiz = optionalQuiz.get();
		quiz.setName(newName);

		quiz = quizRepository.saveAndFlush(quiz);
		return new ResponseEntity<QuizDto>(quizMapper.entityToDto(quiz), HttpStatus.OK);
	}

	private void validateQuizName(String quizName) throws InvalidQuizRequestException {
		if (quizName == null || quizName.trim().length() == 0) {
			throw new InvalidQuizRequestException("Invalid quiz name. Quiz name must not be null or blank.");
		}
	}

	public ResponseEntity<QuestionDto> randomQuestion(Long id) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			return new ResponseEntity<QuestionDto>(HttpStatus.NOT_FOUND);
		}

		Quiz quiz = optionalQuiz.get();
		List<Question> questions = questionRepository.findByQuiz(quiz);

		Random rand = new Random();
		int randInt = rand.nextInt(questions.size());
		return new ResponseEntity<QuestionDto>(questionMapper.entityToDto(questions.get(randInt)), HttpStatus.OK);
	}

	public ResponseEntity<QuizDto> addQuestion(Long id, QuestionDto questionDto)
			throws InvalidQestionRequestException, InvalidAnswerRequestException {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			return new ResponseEntity<QuizDto>(HttpStatus.NOT_FOUND);
		}

		Quiz quiz = optionalQuiz.get();
		Question question = questionMapper.dtoToEntity(questionDto);
		question.setQuiz(quiz);

		validateQuestion(questionDto);
		validateAnswer(questionDto.getAnswer());

		Answer answer = answerRepository.saveAndFlush(question.getAnswer());
		question.setAnswer(answer);
		question = questionRepository.saveAndFlush(question);

		optionalQuiz = quizRepository.findById(id);

		return new ResponseEntity<QuizDto>(quizMapper.entityToDto(optionalQuiz.get()), HttpStatus.OK);
	}

	public ResponseEntity<QuestionDto> deleteQuestion(Long id, Long questionId) {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if (optionalQuiz.isEmpty()) {
			return new ResponseEntity<QuestionDto>(HttpStatus.NOT_FOUND);
		}

		Optional<Question> question = questionRepository.findById(questionId);
		if (question.isEmpty()) {
			return new ResponseEntity<QuestionDto>(HttpStatus.NOT_FOUND);
		}

		questionRepository.delete(question.get());
		return new ResponseEntity<QuestionDto>(questionMapper.entityToDto(question.get()), HttpStatus.OK);
	}

	private void validateAnswer(AnswerDto answerDto) throws InvalidAnswerRequestException {
		if (answerDto.getValue() == null || answerDto.getValue().length() == 0) {
			throw new InvalidAnswerRequestException("Invalid Answer. Answer value should not be null or blank.");
		}
	}

	private void validateQuestion(QuestionDto questionDto) throws InvalidQestionRequestException {
		if (questionDto.getQuestion() == null || questionDto.getQuestion().length() == 0) {
			throw new InvalidQestionRequestException("Invalid question. Question must not be null or blank.");
		}
	}
}
