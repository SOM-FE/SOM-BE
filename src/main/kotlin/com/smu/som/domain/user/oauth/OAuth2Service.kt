package com.smu.som.domain.user.oauth

import com.smu.som.domain.user.dto.Oauth2UserDTO
import org.springframework.stereotype.Service

@Service
interface OAuth2Service {
	fun getOAuth2User(oAuth2AccessToken: String): Oauth2UserDTO
}
