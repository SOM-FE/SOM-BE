package com.smu.som.domain.user.entity

import com.smu.som.domain.user.dto.Oauth2UserDTO
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,

	var oauth2Id: String,

	var ageRange: String?,

	@Enumerated(EnumType.STRING)
	var gender: Gender?,

	var nickname: String
) {
	companion object {
		fun of(oAuth2User: Oauth2UserDTO): User {
			return User(
				oauth2Id = oAuth2User.oauth2Id,
				ageRange = oAuth2User.ageRange,
				gender = Gender.valueOf(oAuth2User.gender),
				nickname = oAuth2User.nickname
			)
		}
	}
}
