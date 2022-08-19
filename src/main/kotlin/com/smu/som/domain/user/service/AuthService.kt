package com.smu.som.domain.user.service

import com.smu.som.common.jwt.service.JwtService
import com.smu.som.domain.user.oauth.OAuth2ServiceFactory
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.SignUpRequestDTO
import com.smu.som.domain.user.dto.SignUpResponseDTO
import com.smu.som.domain.user.entity.Oauth2Provider
import com.smu.som.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
	private val oAuth2ServiceFactory: OAuth2ServiceFactory,
	private val userService: UserService,
	private val jwtService: JwtService
) {
	@Transactional
	fun signUp(signUpRequestDTO: SignUpRequestDTO): SignUpResponseDTO {
		val oAuth2User = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(signUpRequestDTO.oauth2Provider.uppercase()))
			.getOAuth2User(signUpRequestDTO.oauth2AccessToken)

		if (userService.existsByOauth2Id(oAuth2User.oauth2Id)) {
			throw BusinessException(ErrorCode.USER_DUPLICATED)
		}

		val savedUser: User = userService.saveUser(
			User.of(oAuth2User, signUpRequestDTO.maritalStatus, signUpRequestDTO.date)
		)

		return SignUpResponseDTO(
			jwtToken = jwtService.issue(signUpRequestDTO),
			oauth2Id = savedUser.oauth2Id
		)
	}
}
