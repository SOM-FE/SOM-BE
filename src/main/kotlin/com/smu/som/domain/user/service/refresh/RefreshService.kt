package com.smu.som.domain.user.service.refresh

import com.smu.som.domain.user.dto.JwtTokenDTO

interface RefreshService {
	//refresh token 정보를 저장합니다
	fun storeRefresh(jwtToken: JwtTokenDTO, oauth2Id: String)
	//refresh token 정보를 가져옵니다
	fun getRefresh(refreshToken: String): String?
	//refresh token 정보를 삭제합니다
	fun deleteRefresh(refreshToken: String)
}
