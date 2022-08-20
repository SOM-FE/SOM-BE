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
import javax.servlet.http.HttpServletRequest

@Component
@Transactional(readOnly = true)
class JwtResolver(
	@Value("\${jwt.secret}") private val SECRET_KEY: String,
	@Value("\${jwt.access-token-header}") private val ACCESS_TOKEN_HEADER: String,
	@Value("\${jwt.refresh-token-header}") private val REFRESH_TOKEN_HEADER: String,
	private val userDetailsService: UserDetailsServiceImpl
) {
	private val key: Key = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))

	fun getAuthentication(token: String): Authentication {
		val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
		return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
	}

	fun getFromSecurityContextHolder(): UserDetailsImpl {
		return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
	}

	fun resolveAccessToken(request: HttpServletRequest): String {
		return request.getHeader(ACCESS_TOKEN_HEADER).replace("Bearer", "").trim()
	}

	fun resolveRefreshToken(request: HttpServletRequest): String {
		return request.getHeader(REFRESH_TOKEN_HEADER)
	}

	fun parseToken(token: String): Jws<Claims> {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
	}

	private fun getUserPk(token: String): String {
		return try {
			parseToken(token).body.subject
		} catch (e: ExpiredJwtException) {
			e.claims.subject
		}
	}
}
