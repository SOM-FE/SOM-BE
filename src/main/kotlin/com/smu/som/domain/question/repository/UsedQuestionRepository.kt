package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.UsedQuestionDTO
import com.smu.som.domain.question.entity.UsedQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface UsedQuestionRepository : JpaRepository<UsedQuestion, Long> {
	fun findByUserIdAndUsedIsNotNull(userId: String): List<UsedQuestionDTO>
	fun findByUserIdAndPassIsNotNull(userId: String): List<UsedQuestionDTO>
	fun existsByUserId(userId: String): Boolean
	fun deleteByUserId(userId: String)
}
