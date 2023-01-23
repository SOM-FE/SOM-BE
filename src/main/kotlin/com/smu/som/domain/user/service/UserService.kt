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
	//해당 id가 table안에 존재하는지에 대한 여부를 return
	fun existsByOauth2Id(oauth2Id: String)
	= userRepository.existsByOauth2Id(oauth2Id)

	//해당 id를 보유한 user의 정보를 return
	fun findByOauth2Id(oauth2Id: String)
	= userRepository.findByOauth2Id(oauth2Id)

	//user정보 저장
	@Transactional
	fun saveUser(user: User) = userRepository.save(user)
}
