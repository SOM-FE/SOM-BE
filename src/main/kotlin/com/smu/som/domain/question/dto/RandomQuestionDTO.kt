package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty

//질문 목록을 반환하기 위한 DTO
class RandomQuestionDTO(
	@JsonProperty("id")
	var id: Long,

	@JsonProperty("question")
	var question: String
)
