package com.smu.som.domain.question.entity

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import javax.persistence.*

@Entity
@Table(name = "question")
class Question(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	@Enumerated(EnumType.STRING)
	var target: Target,
	var question: String
) {
	fun toReadQuestionDTO(): ReadQuestionDTO {
		return ReadQuestionDTO(
			id = id,
			target = target,
			question = question
		)
	}

	fun toCreateQuestionDTO(): CreateQuestionDTO {
		return CreateQuestionDTO(
			target = target,
			question = question
		)
	}
}
