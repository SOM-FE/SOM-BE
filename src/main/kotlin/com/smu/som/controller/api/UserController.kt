package com.smu.som.controller.api

import com.smu.som.common.annotation.CurrentUser
import com.smu.som.domain.user.entity.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {
	@GetMapping("/validation/adult")
	fun adultValidation(
		@CurrentUser user: User
	): ResponseEntity<Any> {
		return try {
			val isAdult: Boolean = !user.ageRange?.get(0)?.equals("0")!! || !user.ageRange?.get(0)?.equals("1")!!
			ResponseEntity.ok().body(isAdult)
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
