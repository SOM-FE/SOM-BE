package com.smu.som.domain.question.repository

import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionRepositorySupport {
	//지정된 category를 가져옴
	fun findByCategory(category: Category, pageable: Pageable): Page<Question>
}
