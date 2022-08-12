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
	fun getOAuthService(authorizationServer: Oauth2Provider): OAuth2Service {
		return when (authorizationServer) {
			Oauth2Provider.KAKAO -> KakaoOauth2ServiceImpl(restTemplate, objectMapper)
			Oauth2Provider.NAVER -> NaverOauth2ServiceImpl()
		}
	}
}
