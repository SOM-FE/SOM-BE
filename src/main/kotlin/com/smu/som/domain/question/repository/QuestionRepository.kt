package com.smu.som.domain.question.repository

import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository : CrudRepository<Question, Long> {
	fun findByTargetAndCategoryAndIsAdult(target: Target, category: Category, isAdult: String): List<Question>
	fun findByTargetAndCategory(target: Target, category: Category): List<Question>
}
