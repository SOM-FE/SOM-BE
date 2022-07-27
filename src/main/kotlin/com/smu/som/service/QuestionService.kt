package com.smu.som.service

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entities.Target
import com.smu.som.domain.question.repositories.QuestionRepository
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

	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO {
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	fun randomQuestion(target: Target): String {
		val question = questionRepository.findAllByTarget(target)
		val count = question.count()
		val range = (1 until count)
		val idx: Int = range.random()
		return question[idx].question
	}
}
