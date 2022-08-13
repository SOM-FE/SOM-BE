package com.smu.som.domain.question.entity

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import javax.persistence.*

@Entity
@Table(name = "question")
class Question(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var target: Target,
	@Column(nullable = false)
	var question: String,
	@Column(nullable = false, length = 1)
	var adult_status: String
) {
	fun toReadQuestionDTO(): ReadQuestionDTO {
		return ReadQuestionDTO(
			id = id,
			target = target,
			question = question,
			adult_status = adult_status
		)
	}

	fun toCreateQuestionDTO(): CreateQuestionDTO {
		return CreateQuestionDTO(
			target = target,
			question = question,
			adult_status = adult_status
		)
	}
}
