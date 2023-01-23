package com.smu.som.domain.user.service.refresh

import com.smu.som.domain.user.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
	//해당 refreshtoken 정보를 가져옴
	fun findByRefreshToken(refreshToken: String): RefreshToken?
	//해당 id 삭제
	fun deleteByOauth2Id(oauth2Id: String)
}
