package com.smu.som.controller.api

import com.smu.som.domain.admin.dto.LoginRequestDTO
import com.smu.som.domain.admin.service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class LoginController(
	private val adminService: AdminService
) {
	@PostMapping("/login")
	fun login(@RequestBody loginRequestDTO: LoginRequestDTO): Boolean {
		return adminService.login(loginRequestDTO)
	}
}
