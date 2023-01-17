package com.smu.som.common.jwt.user

import com.smu.som.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

//user table에 존재하는 상세 정보에 대한 return
class UserDetailsImpl(
	val user: User
) : UserDetails {
	override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
		return null
	}

	override fun getPassword(): String? {
		return null
	}

	override fun getUsername(): String {
		return user.oauth2Id
	}

	override fun isAccountNonExpired(): Boolean {
		return true
	}

	override fun isAccountNonLocked(): Boolean {
		return true
	}

	override fun isCredentialsNonExpired(): Boolean {
		return true
	}

	override fun isEnabled(): Boolean {
		return true
	}
}
