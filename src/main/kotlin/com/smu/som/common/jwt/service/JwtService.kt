package com.smu.som.common.jwt.service

import com.smu.som.common.jwt.util.JwtProvider
import com.smu.som.domain.user.oauth.OAuth2ServiceFactory
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.JwtTokenDTO
import com.smu.som.domain.user.dto.SignUpRequestDTO
import com.smu.som.domain.user.entity.Oauth2Provider
import com.smu.som.domain.user.entity.User
import com.smu.som.domain.user.service.UserService
import org.springframework.stereotype.Service

@Service
class JwtService (
	private val jwtProvider: JwtProvider,
	private val userService: UserService,
	private val oAuth2ServiceFactory: OAuth2ServiceFactory
	){
	fun issue(oauth2DTO: SignUpRequestDTO): JwtTokenDTO {
		val oAuth2Id: String = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(oauth2DTO.oauth2Provider.uppercase()))
			.getOAuth2User(oauth2DTO.oauth2AccessToken)
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
}
