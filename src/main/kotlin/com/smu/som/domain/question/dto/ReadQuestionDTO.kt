package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Target

//특정 질문의 상세 정보를 확인하기 위한 DTO
class ReadQuestionDTO(
	@JsonProperty("id")
	var id: Long,

	@JsonProperty("target")
	var target: Target,

	@JsonProperty("question")
	var question: String,

	@get:JsonProperty("isAdult")
	@param:JsonProperty("isAdult")
	var isAdult: String,

	@JsonProperty("category")
	var category: Category
)
