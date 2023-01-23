package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty

//user별 받은/패스한 질문을 확인하기 위한 DTO
class GetUsedQuestionDTO(
	@JsonProperty("used")
	var used: ArrayList<Long>,

	@JsonProperty("pass")
	var pass: ArrayList<Long>
)
