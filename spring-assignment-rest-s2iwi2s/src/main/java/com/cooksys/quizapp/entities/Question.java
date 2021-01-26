package com.cooksys.quizapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq")
	@SequenceGenerator(name = "question_id_seq", sequenceName = "question_id_seq", allocationSize = 1)
	private Long id;
	private String question;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_QUESTION_QUIZ_ID"))
	private Quiz quiz;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "answer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_QUESTION_ANSWER_ID"))
	private Answer answer;
}
