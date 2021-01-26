package com.cooksys.quizapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = DuplicateQuizException.class)
	public ResponseEntity<Object> handleDuplicateQuizException(DuplicateQuizException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { InvalidQuizRequestException.class, InvalidQestionRequestException.class,
			InvalidAnswerRequestException.class })
	public ResponseEntity<Object> handleInvalidRequestException(Exception ex,
			WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
	}

//	@ExceptionHandler(value = InvalidQuizRequestException.class)
//	public ResponseEntity<Object> handleInvalidQuizRequestException(InvalidQuizRequestException ex,
//			WebRequest request) {
//		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@ExceptionHandler(value =  InvalidQestionRequestException.class)
//	public ResponseEntity<Object> handleInvalidQestionRequestException(InvalidQestionRequestException ex,
//			WebRequest request) {
//		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
//	}
//
//	@ExceptionHandler(value = InvalidAnswerRequestException.class)
//	public ResponseEntity<Object> handleInvalidAnswerRequestException(InvalidAnswerRequestException ex,
//			WebRequest request) {
//		return handleExceptionInternal(ex, ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
//	}
	
}
