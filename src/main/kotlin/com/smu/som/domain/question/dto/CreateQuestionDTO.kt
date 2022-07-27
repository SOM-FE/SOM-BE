package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entities.Question
import com.smu.som.domain.question.entities.Target;

class CreateQuestionDTO(
	val target: Target,
	val category: String,
	val question: String
) {
	fun toEntity(): Question {
		return Question(
			target = target,
			category = category,
			question = question
		)
	}
}
