package com.smu.som.domain.question.service

import com.smu.som.common.dto.PageResult
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.RandomQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Question
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.repository.QuestionRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
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

	fun getQuestions(category: Category, pageable: Pageable): PageResult<ReadQuestionDTO> {
		val questions = questionRepository.findByCategory(category, pageable)
		return PageResult.ok(questions.map { it.toReadQuestionDTO() })
	}

	fun getQuestion(questionId: Long): ReadQuestionDTO {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		return question.toReadQuestionDTO()
	}

	@Transactional
	fun updateQuestion(questionId: Long, createQuestionDTO: CreateQuestionDTO): ReadQuestionDTO {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		question.update(createQuestionDTO)
		return question.toReadQuestionDTO()
	}

	@Transactional
	fun deleteQuestion(questionId: Long) {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		questionRepository.delete(question)
	}

	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO {
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	fun randomQuestion(target: Target, isAdult: Boolean): List<RandomQuestionDTO> {
		var question: List<RandomQuestionDTO> = if (target == Target.PARENT || target == Target.CHILD) {
			questionRepository.findByTargetInAndIsAdult(listOf(target, Target.COMMON, Target.FAMILY), "n")
		} else {
			if (isAdult) {
				questionRepository.findByTargetIn(listOf(target, Target.COMMON))
			} else {
				questionRepository.findByTargetInAndIsAdult(listOf(target, Target.COMMON), "n")
			}
		}
		return question.shuffled()
	}

	fun randomQuestion(target: Target, category: Category, isAdult: Boolean): List<RandomQuestionDTO> {
		var question: List<RandomQuestionDTO> = if (target == Target.PARENT || target == Target.CHILD) {
			questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON, Target.FAMILY), category, "n")
		} else {
			if (isAdult) {
				questionRepository.findByTargetInAndCategory(listOf(target, Target.COMMON), category)
			} else {
				questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON), category, "n")
			}
		}
		return question.shuffled()
	}
}
