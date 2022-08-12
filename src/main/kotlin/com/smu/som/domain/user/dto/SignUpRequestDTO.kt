package com.smu.som.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpRequestDTO (
	@JsonProperty("oauth2Provider")
	var oauth2Provider: String,

	@JsonProperty("oauth2AccessToken")
	var oauth2AccessToken: String
)
