package com.smu.som.domain.user.dto

data class SignUpResponseDTO(
	val jwtToken: JwtTokenDTO,
	val oauth2Id: String,
)
