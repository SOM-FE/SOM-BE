package com.smu.som.common.jwt.util

import com.smu.som.common.jwt.user.UserDetailsImpl
import com.smu.som.common.jwt.user.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletRequest

//jwt token을 사용하기 위한 기본 setting
@Component
@Transactional(readOnly = true)
class JwtResolver(
	@Value("\${jwt.secret}") private val SECRET_KEY: String,
	@Value("\${jwt.access-token-header}") private val ACCESS_TOKEN_HEADER: String,
	@Value("\${jwt.refresh-token-header}") private val REFRESH_TOKEN_HEADER: String,
	private val userDetailsService: UserDetailsServiceImpl
) {
	//key는 암호화를 통해 보관합니다
	private val key: Key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))

	//인증 정보를 통한 user detail을 가져옵니다
	fun getAuthentication(token: String): Authentication {
		val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
		return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
	}

	//spring security를 이용하여 사용자의 정보를 찾습니다
	fun getFromSecurityContextHolder(): UserDetailsImpl {
		return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
	}

	//access token을 위한 header 정보를 가져옵니다
	fun resolveAccessToken(request: HttpServletRequest): String {
		return request.getHeader(ACCESS_TOKEN_HEADER).replace("Bearer", "").trim()
	}

	//refresh token을 위한 header 정보를 가져옵니다
	fun resolveRefreshToken(request: HttpServletRequest): String {
		return request.getHeader(REFRESH_TOKEN_HEADER)
	}

	//token의 만료 여부를 검사합니다
	fun isExpired(token: String, date :Date): Boolean {
		return try {
			val claims: Jws<Claims> = parseToken(token)
			!claims.body.expiration.before(date)
		} catch (e: Exception) {
			false
		}
	}

	//token을 parsing합니다
	fun parseToken(token: String): Jws<Claims> {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
	}

	//user의 primary key를 가져옵니다
	private fun getUserPk(token: String): String {
		return try {
			parseToken(token).body.subject
		} catch (e: ExpiredJwtException) {
			e.claims.subject
		}
	}
}
