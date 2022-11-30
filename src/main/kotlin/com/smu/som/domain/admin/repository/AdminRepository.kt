package com.smu.som.domain.admin.repository

import com.smu.som.domain.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
	fun findByAdminId(adminId: String) : Admin?
}
