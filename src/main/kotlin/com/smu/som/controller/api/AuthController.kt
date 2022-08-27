package com.smu.som.controller.api

import com.smu.som.common.jwt.util.JwtResolver
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.JwtTokenDTO
import com.smu.som.domain.user.dto.SignInRequestDTO
import com.smu.som.domain.user.dto.SignUpRequestDTO
import com.smu.som.domain.user.dto.SignUpResponseDTO
import com.smu.som.domain.user.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/api/auth")
class AuthController(
	private val authService: AuthService,
	private val jwtResolver: JwtResolver
) {
	@PostMapping("/signup")
	fun signup(@RequestBody signUpRequestDTO: SignUpRequestDTO): ResponseEntity<SignUpResponseDTO> {
		if (!isValidSignUpRequest(signUpRequestDTO)) {
			throw BusinessException(ErrorCode.INVALID_SIGNUP_REQUEST)
		}
		return ResponseEntity.ok().body(authService.signUp(signUpRequestDTO))
	}

	@PostMapping("/signin")
	fun signin(@RequestBody signInRequestDTO: SignInRequestDTO): ResponseEntity<JwtTokenDTO> {
		return ResponseEntity.ok().body(authService.signin(signInRequestDTO))
	}

	@PostMapping("/refresh")
	fun refresh(request: HttpServletRequest): ResponseEntity<JwtTokenDTO> {
		val refreshToken = jwtResolver.resolveRefreshToken(request)
		return ResponseEntity.ok().body(authService.refresh(refreshToken))
	}

	private fun isValidSignUpRequest(signUpRequestDTO: SignUpRequestDTO): Boolean {
		if (signUpRequestDTO.anniversary != null && signUpRequestDTO.maritalStatus == null) {
			return false
		}
		return true
	}
}
