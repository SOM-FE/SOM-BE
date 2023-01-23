package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.RandomQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

//JPA Repository by Question entity
interface QuestionRepository : CrudRepository<Question, Long>, QuestionRepositorySupport {
	//지정된 target과 category를 내포하는 row를 가져옴
	fun findByTargetInAndCategory(@Param("targets") targets: List<Target>, category: Category): List<RandomQuestionDTO>
	//지정된 target과 category 및 성인 여부를 내포하는 row를 가져옴
	fun findByTargetInAndCategoryAndIsAdult(@Param("targets") targets: List<Target>, category: Category, isAdult: String): List<RandomQuestionDTO>
	//지정된 target을 내포하는 row를 가져옴
	fun findByTargetIn(@Param("targets") targets: List<Target>): List<RandomQuestionDTO>
	//지정된 target과 성인 여부를 내포하는 row를 가져옴
	fun findByTargetInAndIsAdult(@Param("targets") targets: List<Target>, isAdult: String): List<RandomQuestionDTO>
	//지정된 id를 보유한 row를 가져옴
	fun findByIdIn(@Param("ids") ids: List<Long>): List<ReadQuestionDTO>
}
