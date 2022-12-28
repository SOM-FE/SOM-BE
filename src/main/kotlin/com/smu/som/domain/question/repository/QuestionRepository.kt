package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.RandomQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface QuestionRepository : CrudRepository<Question, Long>, QuestionRepositorySupport {
	fun findByTargetInAndCategory(@Param("targets") targets: List<Target>, category: Category): List<RandomQuestionDTO>
	fun findByTargetInAndCategoryAndIsAdult(@Param("targets") targets: List<Target>, category: Category, isAdult: String): List<RandomQuestionDTO>
	fun findByTargetIn(@Param("targets") targets: List<Target>): List<RandomQuestionDTO>
	fun findByTargetInAndIsAdult(@Param("targets") targets: List<Target>, isAdult: String): List<RandomQuestionDTO>
	fun findByIdIn(@Param("ids") ids: List<Long>): List<ReadQuestionDTO>
}
