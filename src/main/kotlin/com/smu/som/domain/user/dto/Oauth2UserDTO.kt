package com.smu.som.domain.user.dto

import com.smu.som.domain.user.entity.Gender

data class Oauth2UserDTO(
	val oauth2Id: String,
	val ageRange: String,
	val gender: Gender,
	val nickname: String,
	val email: String
)
