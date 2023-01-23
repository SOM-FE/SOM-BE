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
	//특정 category를 보유한 질문을 찾아 page로 return 하기 위한 함수
	override fun findByCategory(category: Category, pageable: Pageable): Page<Question> {
		val content = getContent(category, pageable)
		val count = getCount(category)

		return PageImpl(content, pageable, count!!)
	}

	//특정 category의 동일 여부를 확인하는 함수
	private fun categoryEq(category: Category): BooleanExpression? {
		if (category == Category.ALL) {
			return null
		}
		return question1.category.eq(category)
	}
	
	//특정 category의 질문 목록에서 content를 추출하기 위한 함수
	private fun getContent(category: Category, pageable: Pageable): List<Question> {
		return queryFactory.selectFrom(question1)
			.where(categoryEq(category))
			.offset(pageable.offset)
			.limit(pageable.pageSize.toLong())
			.fetch()
	}
	
	//특정 category의 질문 갯수를 확인하기 위한 함수
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
