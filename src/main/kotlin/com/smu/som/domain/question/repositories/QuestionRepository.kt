package com.smu.som.domain.question.repositories

import com.smu.som.domain.question.entities.Question
import com.smu.som.domain.question.entities.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository : CrudRepository<Question, Long> {
	fun findByTargetOrTarget(target: Target, target2: Target): List<Question>
	fun findQuestionById(id: Long): String
}
