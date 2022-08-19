package com.smu.som.domain.user.dto

data class Oauth2UserDTO(
	val oauth2Id: String,
	val ageRange: String,
	val gender: String,
	val nickname: String,
	val email: String
)
