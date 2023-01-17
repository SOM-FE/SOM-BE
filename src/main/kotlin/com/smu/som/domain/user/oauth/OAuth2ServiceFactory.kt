package com.smu.som.domain.user.oauth

import com.fasterxml.jackson.databind.ObjectMapper
import com.smu.som.domain.user.entity.Oauth2Provider
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OAuth2ServiceFactory(
	private val restTemplate: RestTemplate,
	private val objectMapper: ObjectMapper
) {
	//user의 가입정보에 따라 kakao와 naver를 구분해 해당 service단으로 넘김
	fun getOAuthService(authorizationServer: Oauth2Provider): OAuth2Service {
		return when (authorizationServer) {
			Oauth2Provider.KAKAO -> KakaoOauth2ServiceImpl(restTemplate, objectMapper)
			Oauth2Provider.NAVER -> NaverOauth2ServiceImpl(restTemplate, objectMapper)
		}
	}
}
