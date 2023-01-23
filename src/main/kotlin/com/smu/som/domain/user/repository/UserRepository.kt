package com.smu.som.domain.user.repository

import com.smu.som.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

//JPA Repository by user entity
interface UserRepository : JpaRepository<User, Long> {
	//해당 id를 보유한 user의 정보를 return
	fun findByOauth2Id(oauth2Id: String): User?
	//해당 id가 table안에 존재하는지에 대한 여부를 return
	fun existsByOauth2Id(oauth2Id: String): Boolean
}
