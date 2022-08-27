package com.smu.som.domain.question.service

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.repository.QuestionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestionService(
	private val questionRepository: QuestionRepository
) {
	fun getQuestions(): List<ReadQuestionDTO> {
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO {
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	fun randomQuestion(target: Target, category: Category, isAdult: Boolean): List<String> {
		var question: List<Question> = if(target == Target.PARENT || target == Target.CHILD) {
			questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON, Target.FAMILY), category, "n")
		} else {
			if (isAdult) {
				questionRepository.findByTargetInAndCategory(listOf(target, Target.COMMON), category)
			} else {
				questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON), category, "n")
			}
		}
		return question.map{ it.question }.shuffled()
	}
}
