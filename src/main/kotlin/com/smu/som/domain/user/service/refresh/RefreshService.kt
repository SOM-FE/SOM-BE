package com.smu.som.domain.user.service.refresh

import com.smu.som.domain.user.dto.JwtTokenDTO

interface RefreshService {
	fun storeRefresh(jwtToken: JwtTokenDTO, oauth2Id: String)
	fun getRefresh(refreshToken: String): String?
	fun deleteRefresh(refreshToken: String)
}
