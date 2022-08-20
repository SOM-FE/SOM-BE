package com.smu.som.domain.user.service

import com.smu.som.domain.user.entity.User
import com.smu.som.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
	private val userRepository: UserRepository
) {
	fun existsByOauth2Id(oauth2Id: String)
	= userRepository.existsByOauth2Id(oauth2Id)

	fun findByOauth2Id(oauth2Id: String)
	= userRepository.findByOauth2Id(oauth2Id)

	@Transactional
	fun saveUser(user: User) = userRepository.save(user)
}
