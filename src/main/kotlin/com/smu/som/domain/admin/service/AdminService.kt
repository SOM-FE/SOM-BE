package com.smu.som.domain.admin.service

import com.smu.som.domain.admin.dto.LoginRequestDTO
import com.smu.som.domain.admin.entity.Admin
import com.smu.som.domain.admin.repository.AdminRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminService(
	private val adminRepository: AdminRepository,
	private val passwordEncoder: PasswordEncoder
) {

	fun login(loginRequestDTO: LoginRequestDTO): Boolean {
		val admin = adminRepository.findByAdminId(loginRequestDTO.adminId)
			?: return false

		if (!isValid(admin, loginRequestDTO.password)) {
			return false
		}

		return true
	}

	fun isValid(admin: Admin, password: String): Boolean {
		if (passwordEncoder.matches(password, admin.password)) {
			return true
		}
		return false
	}
}
