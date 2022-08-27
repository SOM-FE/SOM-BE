package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target;

class CreateQuestionDTO(
	@JsonProperty("target")
	var target: Target,

	@JsonProperty("question")
	var question: String,

	@JsonProperty("isAdult")
	var isAdult: String,

	@JsonProperty("category")
	var category: Category

) {
	fun toEntity(): Question {
		return Question(
			target = target,
			question = question,
			isAdult = isAdult,
			category = category
		)
	}
}
