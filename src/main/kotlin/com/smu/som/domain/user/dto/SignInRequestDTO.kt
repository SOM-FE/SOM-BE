package com.smu.som.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

class SignInRequestDTO (
	@JsonProperty("oauth2Provider")
	val oauth2Provider: String,

	@JsonProperty("oauth2AccessToken")
	val oauth2AccessToken: String
)
