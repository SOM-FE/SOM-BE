package com.smu.som.domain.question.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.smu.som.domain.question.entity.PlayCount

//user별 특정 관계로 플레이한 횟수를 알아내기 위한 DTO
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
	//해당 DTO를 Entity화 시키는 함수
	fun toEntity(): PlayCount{
		return PlayCount(
			userId = userId,
			couple = couple,
			married = married,
			family = family
		)
	}
}
