package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.UsedQuestionDTO
import com.smu.som.domain.question.entity.UsedQuestion
import org.springframework.data.jpa.repository.JpaRepository

//JPA Repository by UsedQuestion entity
interface UsedQuestionRepository : JpaRepository<UsedQuestion, Long> {
	//지정된 userId와 used가 존재하는 row를 가져옴
	fun findByUserIdAndUsedIsNotNull(userId: String): List<UsedQuestionDTO>
	//지정된 userId와 pass가 존재하는 row를 가져옴
	fun findByUserIdAndPassIsNotNull(userId: String): List<UsedQuestionDTO>
}
