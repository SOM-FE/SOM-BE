package com.smu.som.domain.user.service

import com.smu.som.common.jwt.service.JwtService
import com.smu.som.common.util.Constants.Companion.LOGOUT_ACCESS_TOKEN_PREFIX
import com.smu.som.common.util.Constants.Companion.REFRESH_TOKEN_PREFIX
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.*
import com.smu.som.domain.user.entity.Oauth2Provider
import com.smu.som.domain.user.entity.User
import com.smu.som.domain.user.oauth.OAuth2ServiceFactory
import com.smu.som.infra.redis.RedisService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
	private val oAuth2ServiceFactory: OAuth2ServiceFactory,
	private val userService: UserService,
	private val jwtService: JwtService,
	private val redisService: RedisService
) {
	@Value("\${jwt.refresh-token-expiry}")
	private val refreshTokenExpiry: Long = 0

	@Transactional
	fun signUp(signUpRequestDTO: SignUpRequestDTO): SignUpResponseDTO {
		val oAuth2User = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(signUpRequestDTO.oauth2Provider.uppercase()))
			.getOAuth2User(signUpRequestDTO.oauth2AccessToken)

		if (userService.existsByOauth2Id(oAuth2User.oauth2Id)) {
			throw BusinessException(ErrorCode.USER_DUPLICATED)
		}

		val savedUser: User = userService.saveUser(
			User.of(oAuth2User, signUpRequestDTO.maritalStatus, signUpRequestDTO.anniversary)
		)

		val jwtToken = jwtService.issue(signUpRequestDTO.oauth2Provider, signUpRequestDTO.oauth2AccessToken)
		storeRefreshToken(jwtToken, oAuth2User.oauth2Id)

		return SignUpResponseDTO(
			jwtToken = jwtToken,
			oauth2Id = savedUser.oauth2Id
		)
	}

	@Transactional
	fun signin(signInRequestDTO: SignInRequestDTO): JwtTokenDTO {
		val jwtToken = jwtService.issue(signInRequestDTO.oauth2Provider, signInRequestDTO.oauth2AccessToken)

		val oAuth2User = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(signInRequestDTO.oauth2Provider.uppercase()))
			.getOAuth2User(signInRequestDTO.oauth2AccessToken)

		storeRefreshToken(jwtToken, oAuth2User.oauth2Id)
		return jwtToken
	}

	@Transactional
	fun refresh(refreshToken: String): JwtTokenDTO {
		val oAuth2Id = redisService.getValue("$REFRESH_TOKEN_PREFIX:${refreshToken}") as String?
			?: throw BusinessException(ErrorCode.INVALID_JWT)

		val user: User = userService.findByOauth2Id(oAuth2Id)
			?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

		val jwtToken = jwtService.refresh(refreshToken, user.oauth2Id)
		storeRefreshToken(jwtToken, oAuth2Id)
		return jwtToken
	}

	@Transactional
	fun logout(refreshToken: String, accessToken: String) {
		redisService.getValue("$REFRESH_TOKEN_PREFIX:$refreshToken")
			?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
		storeLogoutAccessToken(accessToken)
		redisService.deleteValue("$REFRESH_TOKEN_PREFIX:$refreshToken")
	}

	private fun storeRefreshToken(jwtToken: JwtTokenDTO, oauth2Id: String) {
		redisService.setValue(
			"$REFRESH_TOKEN_PREFIX:${jwtToken.refreshToken}",
			oauth2Id,
			refreshTokenExpiry
		)
	}

	private fun storeLogoutAccessToken(accessToken: String) {
		val remainExpiry = jwtService.getRemainExpiry(accessToken)
		redisService.setValue(
			"$LOGOUT_ACCESS_TOKEN_PREFIX:${accessToken}",
			"logout",
			remainExpiry
		)
	}
}
