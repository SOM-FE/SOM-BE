package com.smu.som.domain.question.entity

import com.smu.som.domain.question.dto.PlayCountDTO
import javax.persistence.*

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
