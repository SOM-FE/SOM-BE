package com.smu.som.domain.question.entity

import javax.persistence.*

@Entity
@Table(name = "used_question")
class UsedQuestion(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	var userId: String,

	var used: Long?,

	var pass: Long?,
)
