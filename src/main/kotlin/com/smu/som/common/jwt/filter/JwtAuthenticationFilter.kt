package com.smu.som.common.jwt.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.smu.som.common.jwt.exception.LogoutJwtException
import com.smu.som.common.jwt.util.JwtResolver
import com.smu.som.common.util.Constants.Companion.LOGOUT_ACCESS_TOKEN_PREFIX
import com.smu.som.controller.error.ErrorCode
import com.smu.som.controller.error.ErrorResponse
import com.smu.som.infra.redis.RedisService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
	private val jwtResolver: JwtResolver,
	private val redisService: RedisService
) : GenericFilterBean() {
	override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		try {
			val accessToken = jwtResolver.resolveAccessToken(request as HttpServletRequest)
			jwtResolver.parseToken(accessToken)
			if (isLogout(accessToken)) {
				throw LogoutJwtException()
			}
			setAuthentication(accessToken)
		} catch (e: ExpiredJwtException) {
			sendErrorResponse(response as HttpServletResponse, ErrorCode.EXPIRED_JWT)
			return
		} catch (e: LogoutJwtException) {
			sendErrorResponse(response as HttpServletResponse, e.errorCode)
			return
		} catch (e: Exception) {
			sendErrorResponse(response as HttpServletResponse, ErrorCode.INVALID_JWT)
			return
		}
		chain.doFilter(request, response)
	}

	private fun setAuthentication(accessToken: String) {
		val authentication = jwtResolver.getAuthentication(accessToken)
		SecurityContextHolder.getContext().authentication = authentication
	}

	private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
		val errorResponse = ErrorResponse.of(errorCode)
		val mapper = jacksonObjectMapper()
		response.characterEncoding = "UTF-8"
		response.status = errorCode.status.value()
		response.contentType = MediaType.APPLICATION_JSON_VALUE
		response.writer.write(mapper.writeValueAsString(errorResponse))
	}

	private fun isLogout(accessToken: String): Boolean {
		redisService.getValue("${LOGOUT_ACCESS_TOKEN_PREFIX}:$accessToken")
			?: return false
		return true
	}
}
