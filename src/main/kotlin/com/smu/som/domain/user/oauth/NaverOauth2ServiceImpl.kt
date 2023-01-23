package com.smu.som.domain.user.oauth

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.smu.som.common.util.Constants
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.Oauth2UserDTO
import com.smu.som.domain.user.entity.Gender
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

class NaverOauth2ServiceImpl(
	private val restTemplate: RestTemplate,
	private val objectMapper: ObjectMapper
) : OAuth2Service {
	private val NAVER_USER_INFO_URI: String = "https://openapi.naver.com/v1/nid/me"

	//특정 user의 정보를 불러옴
	override fun getOAuth2User(oAuth2AccessToken: String): Oauth2UserDTO {
		val userJson = getProfileInfoFromProvider(oAuth2AccessToken)
		return buildOAuth2User(userJson)
	}

	//user의 정보를 해당 provider에 맞게 불러옵니다.
	private fun getProfileInfoFromProvider(oAuth2AccessToken: String): JsonNode {
		val response: ResponseEntity<String> = try {
			restTemplate.postForEntity(
				NAVER_USER_INFO_URI,
				buildRequest(oAuth2AccessToken),
				String::class.java)
		} catch (e: HttpClientErrorException) {
			throw BusinessException(ErrorCode.OAUTH2_FAIL_EXCEPTION)
		}

		//기존 형태를 json으로 변경하여 return
		return try {
			objectMapper.readTree(response.body)
		} catch (e: JsonProcessingException) {
			throw BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)
		}
	}

	//header설정
	private fun buildRequest(oAuth2AccessToken: String): HttpEntity<*> {
		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
		headers.setBearerAuth(oAuth2AccessToken)
		return HttpEntity(null, headers)
	}

	//user의 정보를 OauthUserDTO로 return
	private fun buildOAuth2User(jsonNode: JsonNode): Oauth2UserDTO {
		val naver_account = jsonNode.get("response")
		val oAuth2Id = naver_account.get("id").asText()
		val nickname = naver_account.get("nickname").asText()
		val gender = getGender(naver_account.get("gender").asText())
		val ageRange = naver_account.get("age").asText().replace("-", "~")
		val email = naver_account.get("email").asText()

		return Oauth2UserDTO(
			oauth2Id = "${Constants.PROVIDER_NAVER_PREFIX}:$oAuth2Id",
			nickname = nickname,
			ageRange = ageRange,
			gender = gender,
			email = email
		)
	}

	//성별 return
	private fun getGender(initial: String) = when (initial) {
		"F" -> Gender.FEMALE
		"M" -> Gender.MALE
		else -> Gender.NONE
	}
}
