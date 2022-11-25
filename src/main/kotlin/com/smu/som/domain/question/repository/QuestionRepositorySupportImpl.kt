package com.smu.som.domain.question.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.QQuestion.question1
import com.smu.som.domain.question.entity.Question
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class QuestionRepositorySupportImpl(
	private val queryFactory: JPAQueryFactory
) : QuestionRepositorySupport, QuerydslRepositorySupport(Question::class.java) {
	override fun findByCategory(category: Category, pageable: Pageable): Page<Question> {
		val content = getContent(category, pageable)
		val count = getCount(category)

		return PageImpl(content, pageable, count!!)
	}

	private fun categoryEq(category: Category): BooleanExpression? {
		if (category == Category.ALL) {
			return null
		}
		return question1.category.eq(category)
	}

	private fun getContent(category: Category, pageable: Pageable): List<Question> {
		return queryFactory.selectFrom(question1)
			.where(categoryEq(category))
			.offset(pageable.offset)
			.limit(pageable.pageSize.toLong())
			.fetch()
	}

	private fun getCount(category: Category): Long? {
		return queryFactory
			.select(question1.count())
			.from(question1)
			.where(
				categoryEq(category)
			)
			.fetchOne()
	}
}
