package com.smu.som.controller.api

import com.smu.som.domain.admin.dto.LoginRequestDTO
import com.smu.som.domain.admin.service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class LoginController(
	private val adminService: AdminService
) {
	//admin page 로그인을 위한 요청을 보내는 api
	@PostMapping("/login")
	fun login(@RequestBody loginRequestDTO: LoginRequestDTO): Boolean {
		return adminService.login(loginRequestDTO)
	}
}
