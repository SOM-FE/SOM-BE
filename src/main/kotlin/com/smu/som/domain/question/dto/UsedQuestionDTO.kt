package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.UsedQuestion

//특정 user별 이미 받은/패스한 질문을 알아보기 위한 DTO
class UsedQuestionDTO(
	@JsonProperty("userId")
	var userId: String,

	@JsonProperty("used")
	var used: Long?,

	@JsonProperty("pass")
	var pass: Long?
) {
	//해당 DTO를 Entity화 시키는 함수
	fun toEntity(): UsedQuestion{
		return UsedQuestion(
			userId = userId,
			used = used,
			pass = pass
		)
	}
}
