package com.smu.som.domain.admin.repository

import com.smu.som.domain.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

//JPA Repository by Admin entity
interface AdminRepository : JpaRepository<Admin, Long> {
	//특정 id의 row를 가져옴
	fun findByAdminId(adminId: String) : Admin?
}
