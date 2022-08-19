package com.smu.som.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class SignUpRequestDTO (
	@JsonProperty("oauth2Provider")
	val oauth2Provider: String,

	@JsonProperty("oauth2AccessToken")
	val oauth2AccessToken: String,

	val maritalStatus: Boolean,

	val date: LocalDate
)
