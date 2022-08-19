package com.smu.som.domain.user.entity

import com.smu.som.domain.user.dto.Oauth2UserDTO
import java.time.LocalDate
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

	var nickname: String,

	var email: String?,

	var maritalStatus: Boolean = false,

	var date: LocalDate? = null

) {
	companion object {
		fun of(oAuth2User: Oauth2UserDTO, maritalStatus: Boolean, date: LocalDate?): User {
			return User(
				oauth2Id = oAuth2User.oauth2Id,
				ageRange = oAuth2User.ageRange,
				gender = Gender.valueOf(oAuth2User.gender),
				nickname = oAuth2User.nickname,
				email = oAuth2User.email,
				maritalStatus = maritalStatus,
				date = date
			)
		}
	}
}
