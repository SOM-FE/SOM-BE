package com.smu.som.domain.question.repository

import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface QuestionRepository : CrudRepository<Question, Long>, QuestionRepositorySupport {
	fun findByTargetInAndCategory(@Param("targets") targets: List<Target>, category: Category): List<Question>
	fun findByTargetInAndCategoryAndIsAdult(@Param("targets") targets: List<Target>, category: Category, isAdult: String): List<Question>
	fun findByTargetIn(@Param("targets") targets: List<Target>): List<Question>
	fun findByTargetInAndIsAdult(@Param("targets") targets: List<Target>, isAdult: String): List<Question>
}
