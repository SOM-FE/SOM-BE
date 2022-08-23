package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Target;

class ReadQuestionDTO(
	@JsonProperty("id")
	var id: Long,

	@JsonProperty("target")
	var target: Target,

	@JsonProperty("question")
	var question: String,

	@JsonProperty("isAdult")
	var isAdult: String,

	@JsonProperty("category")
	var category: Category
)
