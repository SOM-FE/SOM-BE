package com.smu.som.domain.question.entity

import javax.persistence.*

//user별 특정 관계에 대한 플레이 횟수를 저장하기 위한 DB table
@Entity
@Table(name = "play_count")
class PlayCount(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	var userId: String,

	var couple: Long?,

	var married: Long?,

	var family: Long?
)
