package com.smu.som.domain.user.entity

import com.smu.som.domain.user.dto.Oauth2UserDTO
import java.util.*
import javax.persistence.*

//user의 정보를 table에 담기 위한 Entity
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

	var nickname: String,

	var email: String?,

	var maritalStatus: Boolean? = null,

	var anniversary: Date? = null

) {
	companion object {
		fun of(oAuth2User: Oauth2UserDTO, maritalStatus: Boolean?, anniversary: Date?): User {
			return User(
				oauth2Id = oAuth2User.oauth2Id,
				ageRange = oAuth2User.ageRange,
				gender = oAuth2User.gender,
				nickname = oAuth2User.nickname,
				email = oAuth2User.email,
				maritalStatus = maritalStatus,
				anniversary = anniversary
			)
		}
	}
}
