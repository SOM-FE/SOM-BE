package com.smu.som.domain.user.oauth

import com.smu.som.domain.user.dto.Oauth2UserDTO
import org.springframework.stereotype.Service

@Service
interface OAuth2Service {
	//user의 oauth토큰을 가져옴
	fun getOAuth2User(oAuth2AccessToken: String): Oauth2UserDTO
}
