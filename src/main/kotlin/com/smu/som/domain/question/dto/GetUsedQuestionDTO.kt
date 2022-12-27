package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GetUsedQuestionDTO(
	@JsonProperty("used")
	var used: ArrayList<Long>,

	@JsonProperty("pass")
	var pass: ArrayList<Long>
)
