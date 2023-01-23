package com.smu.som.domain.user.service

import com.smu.som.common.jwt.service.JwtService
import com.smu.som.common.util.Constants.Companion.REFRESH_TOKEN_PREFIX
import com.smu.som.domain.user.oauth.OAuth2ServiceFactory
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.*
import com.smu.som.domain.user.entity.Oauth2Provider
import com.smu.som.domain.user.entity.User
import com.smu.som.domain.user.service.refresh.RefreshService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
	private val oAuth2ServiceFactory: OAuth2ServiceFactory,
	private val userService: UserService,
	private val jwtService: JwtService,
	private val refreshService: RefreshService
) {
	
	//회원가입을 위한 함수
	@Transactional
	fun signUp(signUpRequestDTO: SignUpRequestDTO): SignUpResponseDTO {
		//해당 유저의 정보를 생성하여 가져옵니다
		val oAuth2User = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(signUpRequestDTO.oauth2Provider.uppercase()))
			.getOAuth2User(signUpRequestDTO.oauth2AccessToken)
		
		//해당 id가 존재할 경우 error 발생
		if (userService.existsByOauth2Id(oAuth2User.oauth2Id)) {
			throw BusinessException(ErrorCode.USER_DUPLICATED)
		}

		//유저 정보 신규 저장
		val savedUser: User = userService.saveUser(
			User.of(oAuth2User, signUpRequestDTO.maritalStatus, signUpRequestDTO.anniversary)
		)

		val jwtToken = jwtService.issue(signUpRequestDTO.oauth2Provider, signUpRequestDTO.oauth2AccessToken)
		refreshService.storeRefresh(jwtToken, oAuth2User.oauth2Id)

		return SignUpResponseDTO(
			jwtToken = jwtToken,
			oauth2Id = savedUser.oauth2Id
		)
	}

	//로그인을 위한 함수
	@Transactional
	fun signin(signInRequestDTO: SignInRequestDTO): JwtTokenDTO {
		//jwt token에 존재하는 정보를 통해 로그인합니다
		val jwtToken = jwtService.issue(signInRequestDTO.oauth2Provider, signInRequestDTO.oauth2AccessToken)

		//해당 유저의 정보를 생성하여 가져옵니다
		val oAuth2User = oAuth2ServiceFactory
			.getOAuthService(Oauth2Provider.valueOf(signInRequestDTO.oauth2Provider.uppercase()))
			.getOAuth2User(signInRequestDTO.oauth2AccessToken)
		
		refreshService.storeRefresh(jwtToken, oAuth2User.oauth2Id)
		return jwtToken
	}

	//해당 토큰의 만료를 대비해 refresh 하는 하뭇
	@Transactional
	fun refresh(refreshToken: String): JwtTokenDTO {
		//refresh 정보를 가져오지 못할 경우 error 발생
		val oAuth2Id = refreshService.getRefresh("$REFRESH_TOKEN_PREFIX:${refreshToken}")
			?: throw BusinessException(ErrorCode.INVALID_JWT)

		//user 정보를 가져오지 못할 경우 error 발생
		val user: User = userService.findByOauth2Id(oAuth2Id)
			?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

		//token을 새로 refresh 합니다
		val jwtToken = jwtService.refresh(refreshToken, user.oauth2Id)
		//새로 refresh 된 정보를 저장
		refreshService.storeRefresh(jwtToken, oAuth2Id)
		return jwtToken
	}

	//로그아웃 함수
	@Transactional
	fun logout(refreshToken: String, accessToken: String) {
		refreshService.deleteRefresh("$REFRESH_TOKEN_PREFIX:$refreshToken")
	}
}
