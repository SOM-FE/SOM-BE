package com.smu.som.common.jwt.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.smu.som.common.jwt.exception.LogoutJwtException
import com.smu.som.common.jwt.util.JwtResolver
import com.smu.som.controller.error.ErrorCode
import com.smu.som.controller.error.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//jwt를 통한 인증 요청 및 응답 시 사용될 filter 정의
class JwtAuthenticationFilter(
	private val jwtResolver: JwtResolver
) : GenericFilterBean() {
	//token과 관련된 인증 처리 시 발생할 error에 대한 handler
	override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		try {
			val accessToken = jwtResolver.resolveAccessToken(request as HttpServletRequest)
			jwtResolver.parseToken(accessToken)
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

	//access token을 통한 인증 정보 set
	private fun setAuthentication(accessToken: String) {
		val authentication = jwtResolver.getAuthentication(accessToken)
		SecurityContextHolder.getContext().authentication = authentication
	}

	//error response와 해당 HTTP 상태 코드를 response로 작성
	private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
		val errorResponse = ErrorResponse.of(errorCode)
		val mapper = jacksonObjectMapper()
		response.characterEncoding = "UTF-8"
		response.status = errorCode.status.value()
		response.contentType = MediaType.APPLICATION_JSON_VALUE
		response.writer.write(mapper.writeValueAsString(errorResponse))
	}
}
