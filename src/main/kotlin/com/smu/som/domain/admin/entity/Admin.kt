package com.smu.som.domain.admin.entity

import javax.persistence.*

//admin 검증을 위한 id/pw DB table
@Entity
@Table(name = "admin")
class Admin(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,

	var adminId: String,

	var password: String,
)
