package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entities.Question
import com.smu.som.domain.question.entities.Target;

class CreateQuestionDTO(
	var target: Target,
	var question: String
) {
	fun toEntity(): Question {
		return Question(
			target = target,
			question = question
		)
	}
}
