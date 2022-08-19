package com.smu.som.domain.user.oauth

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.smu.som.domain.user.dto.Oauth2UserDTO
import com.smu.som.common.util.Constants.Companion.PROVIDER_KAKAO_PREFIX
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class KakaoOauth2ServiceImpl(
	private val restTemplate: RestTemplate,
	private val objectMapper: ObjectMapper
) : OAuth2Service {
	private val KAKAO_USER_INFO_URI: String = "https://kapi.kakao.com/v2/user/me"

	override fun getOAuth2User(oAuth2AccessToken: String): Oauth2UserDTO {
		val userJson = getProfileInfoFromProvider(oAuth2AccessToken)
		return buildOAuth2User(userJson)
	}

	private fun getProfileInfoFromProvider(oAuth2AccessToken: String): JsonNode {
		val response: ResponseEntity<String> = restTemplate.postForEntity(
			KAKAO_USER_INFO_URI,
			buildRequest(oAuth2AccessToken),
			String::class.java
		)
		return try {
			objectMapper.readTree(response.body)
		} catch (e: JsonProcessingException) {
			throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
		}
	}

	private fun buildRequest(oAuth2AccessToken: String): HttpEntity<*> {
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
		headers.setBearerAuth(oAuth2AccessToken)
		return HttpEntity(null, headers)
	}

	private fun buildOAuth2User(jsonNode: JsonNode): Oauth2UserDTO {
		val kakao_account = jsonNode.get("kakao_account")
		val oAuth2Id = jsonNode.get("id").asText()
		val nickname = kakao_account
			.get("profile")
			.get("nickname")
			.asText()
		val gender = kakao_account.get("gender").asText().uppercase()
		val ageRange = kakao_account.get("age_range").asText()
		val email = kakao_account.get("email").asText()

		return Oauth2UserDTO(
			oauth2Id = "$PROVIDER_KAKAO_PREFIX:$oAuth2Id",
			nickname = nickname,
			ageRange = ageRange,
			gender = gender,
			email = email
		)
	}
}
