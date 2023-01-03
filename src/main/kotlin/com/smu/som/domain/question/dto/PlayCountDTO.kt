package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.PlayCount

class PlayCountDTO(
	@JsonProperty("userId")
	var userId: String,

	@JsonProperty("couple")
	var couple: Long = 0,

	@JsonProperty("married")
	var married: Long = 0,

	@JsonProperty("family")
	var family: Long = 0
) {
	fun toEntity(): PlayCount{
		return PlayCount(
			userId = userId,
			couple = couple,
			married = married,
			family = family
		)
	}
}
