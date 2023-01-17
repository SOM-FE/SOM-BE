package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target

//질문 생성을 위한 DTO
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
	//해당 DTO를 Entity화 시키는 함수
	fun toEntity(): Question {
		return Question(
			target = target,
			question = question,
			isAdult = isAdult,
			category = category
		)
	}
}
