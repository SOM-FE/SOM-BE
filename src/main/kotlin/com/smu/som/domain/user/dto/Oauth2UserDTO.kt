package com.smu.som.domain.user.dto

import com.smu.som.domain.user.entity.Gender

//oauth를 통한 로그인 및 회원가입 시 유저의 정보를 담기 위한 DTO
data class Oauth2UserDTO(
	val oauth2Id: String,
	val ageRange: String,
	val gender: Gender,
	val nickname: String,
	val email: String
)
