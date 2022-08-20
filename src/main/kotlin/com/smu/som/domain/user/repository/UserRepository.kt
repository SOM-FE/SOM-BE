package com.smu.som.domain.user.repository

import com.smu.som.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
	fun findByOauth2Id(oauth2Id: String): User?

	fun existsByOauth2Id(oauth2Id: String): Boolean
}
