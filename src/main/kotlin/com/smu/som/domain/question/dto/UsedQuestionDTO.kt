package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.UsedQuestion

class UsedQuestionDTO(
	@JsonProperty("userId")
	var userId: String,

	@JsonProperty("used")
	var used: Long?,

	@JsonProperty("pass")
	var pass: Long?
) {
	fun toEntity(): UsedQuestion{
		return UsedQuestion(
			userId = userId,
			used = used,
			pass = pass
		)
	}
}
