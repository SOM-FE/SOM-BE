package com.smu.som.domain.question.entity

import javax.persistence.*

//user별 받은/패스한 질문을 저장하기 위한 DB table
@Entity
@Table(name = "used_question")
class UsedQuestion(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	var userId: String,

	var used: Long?,

	var pass: Long?
)
