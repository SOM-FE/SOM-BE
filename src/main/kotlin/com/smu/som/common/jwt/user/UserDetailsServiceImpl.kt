package com.smu.som.common.jwt.user

import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
	private val userRepository: UserRepository
) : UserDetailsService {
	//username을 통해 user의 상세정보를 return합니다
	override fun loadUserByUsername(username: String): UserDetails {
		return UserDetailsImpl(userRepository.findByOauth2Id(username)
			?: throw BusinessException(ErrorCode.USER_NOT_FOUND))
	}
}
