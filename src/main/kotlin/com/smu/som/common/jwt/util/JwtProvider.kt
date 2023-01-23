package com.smu.som.common.jwt.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

//jwt token을 공급하기 위한 기본 setting
@Component
@Transactional(readOnly = true)
class JwtProvider(
	@Value("\${jwt.secret}") private val secretKey: String,
	@Value("\${jwt.access-token-expiry}") private val accessTokenValidTime: Int,
	@Value("\${jwt.refresh-token-expiry}") private val refreshTokenValidTime: Long
) {
	private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

	//access token을 생성합니다
	fun createAccessToken(oAuth2Id: String): String {
		val claims: Claims = Jwts.claims().setSubject(oAuth2Id)
		val now = Date()
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(Date(now.time + accessTokenValidTime))
			.signWith(key)
			.compact()
	}

	//refresh token을 생성합니다
	fun createRefreshToken(): String {
		val now = Date()
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(Date(now.time + refreshTokenValidTime))
			.signWith(key)
			.compact()
	}
}
