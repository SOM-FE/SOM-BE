package com.smu.som.domain.question.repositories

import com.smu.som.domain.question.entities.Question
import com.smu.som.domain.question.entities.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository : CrudRepository<Question, Long> {
	fun findAllByTarget(target: Target): List<Question>
}
