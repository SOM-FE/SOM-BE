package com.smu.som.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

//oauth를 이용해 회원가입 요청을 하기 위한 DTO
data class SignUpRequestDTO (
	@JsonProperty("oauth2Provider")
	val oauth2Provider: String,

	@JsonProperty("oauth2AccessToken")
	val oauth2AccessToken: String,

	@JsonProperty("maritalStatus")
	val maritalStatus: Boolean? = null,

	@JsonProperty("anniversary")
	val anniversary: Date? = null
)
