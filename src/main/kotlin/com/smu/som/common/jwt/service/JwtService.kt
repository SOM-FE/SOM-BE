package com.smu.som.common.jwt.service

import com.smu.som.common.jwt.util.JwtProvider
import com.smu.som.common.jwt.util.JwtResolver
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.JwtTokenDTO
import com.smu.som.domain.user.entity.Oauth2Provider
import com.smu.som.domain.user.entity.User
import com.smu.som.domain.user.oauth.OAuth2ServiceFactory
import com.smu.som.domain.user.service.UserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
	private val jwtProvider: JwtProvider,
	private val jwtResolver: JwtResolver,
	private val userService: UserService,
	private val oAuth2ServiceFactory: OAuth2ServiceFactory
) {
	//access token을 통해 해당 유저의 정보를 불러옵니다
	fun issue(oauth2Provider: String, oauth2AccessToken: String): JwtTokenDTO {
		val oAuth2Id: String = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(oauth2Provider.uppercase()))
			.getOAuth2User(oauth2AccessToken)
			.oauth2Id

		val user: User = userService.findByOauth2Id(oAuth2Id)
			?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

		val accessToken: String = jwtProvider.createAccessToken(user.oauth2Id)
		val refreshToken: String = jwtProvider.createRefreshToken()

		return JwtTokenDTO(
			accessToken = accessToken,
			refreshToken = refreshToken
		)
	}

	//해당 id에 저장된 user의 token 정보를 refresh 합니다
	fun refresh(refreshToken: String, oauth2Id: String): JwtTokenDTO {
		var localRefreshToken = refreshToken
		if (isValidate(refreshToken)) {
			throw BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN)
		}
		val accessToken = jwtProvider.createAccessToken(oauth2Id)

		if (isRefreshable(refreshToken)) {
			localRefreshToken = jwtProvider.createRefreshToken()
		}
		return JwtTokenDTO(accessToken, localRefreshToken)
	}

	//token의 남은 만료 기한을 return 합니다
	fun getRemainExpiry(token: String): Long {
		val expiration = jwtResolver.parseToken(token).body.expiration
		val now = Date()
		return expiration.time - now.time
	}
	
	//token의 만료 여부를 검사합니다
	private fun isValidate(refreshToken: String): Boolean {
		val now = Date()
		return !jwtResolver.isExpired(refreshToken, now)
	}

	//해당 toke이 refresh가 필요한지에 대한 여부를 검사합니다
	private fun isRefreshable(refreshToken: String): Boolean {
		val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA)
		calendar.time = Date()
		calendar.add(Calendar.DATE, 3)
		return !jwtResolver.isExpired(refreshToken, calendar.time)
	}
}
