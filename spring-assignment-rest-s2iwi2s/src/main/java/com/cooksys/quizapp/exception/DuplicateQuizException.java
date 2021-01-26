package com.cooksys.quizapp.exception;

public class DuplicateQuizException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3255555382304260424L;

	public DuplicateQuizException() {
		super("Quiz name already exist");
	}

}
