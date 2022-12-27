package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty

class RandomQuestionDTO(
	@JsonProperty("id")
	var id: Long,

	@JsonProperty("question")
	var question: String
)
