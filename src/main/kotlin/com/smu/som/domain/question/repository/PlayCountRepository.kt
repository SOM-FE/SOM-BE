package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.PlayCountDTO
import com.smu.som.domain.question.entity.PlayCount
import org.springframework.data.jpa.repository.JpaRepository

interface PlayCountRepository : JpaRepository<PlayCount, Long> {
	fun existsByUserId(userId: String): Boolean
	fun findByUserId(userId: String): PlayCountDTO
	fun deleteByUserId(userId: String)
}
