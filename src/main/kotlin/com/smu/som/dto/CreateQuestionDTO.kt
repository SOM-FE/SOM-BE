package com.smu.som.dto

import com.smu.som.entities.QuestionEntity
import com.smu.som.entities.Target;

data class CreateQuestionDTO(
	val target: Target,
	val category: String,
	val question: String
) {
	fun toEntity(): QuestionEntity{
		return QuestionEntity(
			target = target,
			category= category,
			question = question
		)
	}
}
