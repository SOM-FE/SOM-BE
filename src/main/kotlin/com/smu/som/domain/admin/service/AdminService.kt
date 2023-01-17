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
	//admin page login 성공 여부를 반환합니다.
	fun login(loginRequestDTO: LoginRequestDTO): Boolean {
		val admin = adminRepository.findByAdminId(loginRequestDTO.adminId)
			?: return false

		if (!isValid(admin, loginRequestDTO.password)) {
			return false
		}

		return true
	}

	//DB에 저장되어 있는 admin 정보와 일치한지 validation을 진행합니다.
	fun isValid(admin: Admin, password: String): Boolean {
		//password는 BCrypt방식으로 암호화를 통해 비교를 진행합니다.
		if (passwordEncoder.matches(password, admin.password)) {
			return true
		}
		return false
	}
}
