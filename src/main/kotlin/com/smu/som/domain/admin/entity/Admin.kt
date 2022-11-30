package com.smu.som.domain.admin.entity

import javax.persistence.*

@Entity
@Table(name = "admin")
class Admin(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,

	var adminId: String,

	var password: String,
)
