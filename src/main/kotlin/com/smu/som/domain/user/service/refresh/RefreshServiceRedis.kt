package com.smu.som.domain.user.service.refresh

import com.smu.som.common.jwt.service.JwtService
import com.smu.som.common.util.Constants
import com.smu.som.common.util.Constants.Companion.REFRESH_TOKEN_PREFIX
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.JwtTokenDTO
import com.smu.som.infra.redis.RedisService
import org.springframework.beans.factory.annotation.Value

class RefreshServiceRedis(
	private val redisService: RedisService,
	private val jwtService: JwtService
) : RefreshService {

	@Value("\${jwt.refresh-token-expiry}")
	private val refreshTokenExpiry: Long = 0

	override fun storeRefresh(jwtToken: JwtTokenDTO, oauth2Id: String) {
		redisService.setValue(
			"$REFRESH_TOKEN_PREFIX:${jwtToken.refreshToken}",
			oauth2Id,
			refreshTokenExpiry
		)
	}

	override fun getRefresh(refreshToken: String): String? {
		return redisService.getValue("$REFRESH_TOKEN_PREFIX:${refreshToken}") as String?
	}

	override fun deleteRefresh(refreshToken: String) {
		redisService.getValue("$REFRESH_TOKEN_PREFIX:${refreshToken}")
			?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)

		redisService.deleteValue("$REFRESH_TOKEN_PREFIX:$refreshToken")
	}

	fun storeLogoutAccessToken(accessToken: String) {
		val remainExpiry = jwtService.getRemainExpiry(accessToken)
		redisService.setValue(
			"${Constants.LOGOUT_ACCESS_TOKEN_PREFIX}:${accessToken}",
			"logout",
			remainExpiry
		)
	}

	fun isLogout(accessToken: String): Boolean {
		redisService.getValue("${Constants.LOGOUT_ACCESS_TOKEN_PREFIX}:$accessToken")
			?: return false
		return true
	}
}
