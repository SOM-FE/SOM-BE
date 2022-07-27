package com.smu.som.dto

import com.smu.som.entities.Question
import com.smu.som.entities.Target;

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
