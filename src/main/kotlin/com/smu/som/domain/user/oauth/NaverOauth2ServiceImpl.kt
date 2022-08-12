package com.smu.som.domain.user.oauth

import com.smu.som.domain.user.dto.Oauth2UserDTO

class NaverOauth2ServiceImpl(

) : OAuth2Service {
	override fun getOAuth2User(oAuth2AccessToken: String): Oauth2UserDTO {
		TODO("Not yet implemented")
	}
}
