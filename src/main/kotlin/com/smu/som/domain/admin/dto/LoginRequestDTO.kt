package com.smu.som.domain.admin.dto

import com.fasterxml.jackson.annotation.JsonProperty

//admin page 로그인을 위한 DTO
data class LoginRequestDTO (
	@JsonProperty("adminId")
	val adminId: String,

	@JsonProperty("password")
	val password: String
)
