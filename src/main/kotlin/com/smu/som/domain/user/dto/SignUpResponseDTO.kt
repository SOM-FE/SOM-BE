package com.smu.som.domain.user.dto

//oauth를 이용해 회원가입 이후 해당 정보를 response 하기 위한 DTO
data class SignUpResponseDTO(
	val jwtToken: JwtTokenDTO,
	val oauth2Id: String,
)
