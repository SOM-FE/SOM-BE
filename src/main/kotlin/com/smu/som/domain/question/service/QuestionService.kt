package com.smu.som.domain.question.service

import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entities.Target
import com.smu.som.domain.question.repositories.QuestionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
	private val questionRepository: QuestionRepository
) {
	@Transactional(readOnly = true)
	fun getQuestions(): List<ReadQuestionDTO> {
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	@Transactional(readOnly = true)
	fun getQuestion(id: Long): String {
		val question = questionRepository.findByIdOrNull(id)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUNT)

		return question.question
	}

	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO {
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	@Transactional(readOnly = true)
	fun randomQuestion(target: Target): MutableList<Long> {
		var question = questionRepository.findByTargetOrTarget(target, Target.COMMON)
		var questionNoList = question.map { it.id }
		return questionNoList.shuffled().toMutableList()
	}
}
