package com.smu.som.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

//oauth를 이용해 로그인을 하기 위한 DTO
data class SignInRequestDTO (
	@JsonProperty("oauth2Provider")
	val oauth2Provider: String,

	@JsonProperty("oauth2AccessToken")
	val oauth2AccessToken: String
)
