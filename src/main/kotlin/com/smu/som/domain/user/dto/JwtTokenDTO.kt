package com.smu.som.domain.user.dto

//jwt Token 정보를 담은 DTO
data class JwtTokenDTO(
	val accessToken: String,
	val refreshToken: String
)
