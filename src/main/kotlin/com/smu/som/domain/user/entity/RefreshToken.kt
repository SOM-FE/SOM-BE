package com.smu.som.domain.user.entity

import com.smu.som.common.entity.BaseEntity
import javax.persistence.*

//table에 특정 유저의 oauthId 및 Token의 유효기간 및 만료 정보를 저장하기 위한 Entity
@Entity
@Table(name = "refresh_token")
class RefreshToken(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var refreshTokenId: Long? = null,

	var refreshToken: String,

	var oauth2Id: String
) : BaseEntity()
