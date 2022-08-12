package com.smu.som.controller.api

import com.smu.som.domain.user.dto.SignUpRequestDTO
import com.smu.som.domain.user.dto.SignUpResponseDTO
import com.smu.som.domain.user.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
	private val authService: AuthService
) {
	@PostMapping("/signup")
	fun signup(@RequestBody oAuth2DTO: SignUpRequestDTO): ResponseEntity<SignUpResponseDTO> {
		return ResponseEntity.ok().body(authService.signUp(oAuth2DTO))
	}
}
