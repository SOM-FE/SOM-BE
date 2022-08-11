package com.smu.som.domain.question.repository

import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository : CrudRepository<Question, Long> {
	fun findByTargetOrTarget(target: Target, target2: Target): List<Question>
	fun findQuestionById(id: Long): String
}
