package com.smu.som.domain.admin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequestDTO (
	@JsonProperty("adminId")
	val adminId: String,
	@JsonProperty("password")
	val password: String
)
