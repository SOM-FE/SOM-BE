package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target;

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
