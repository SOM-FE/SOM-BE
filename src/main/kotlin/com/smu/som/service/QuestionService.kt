package com.smu.som.service

import com.smu.som.dto.CreateQuestionDTO
import com.smu.som.dto.ReadQuestionDTO
import com.smu.som.entities.Target
import com.smu.som.repositories.QuestionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(private val questionRepository: QuestionRepository) {

	@Transactional(readOnly=true)
	fun getQuestions(): List<ReadQuestionDTO>{
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	@Transactional(readOnly=true)
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO{
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	@Transactional(readOnly=true)
	fun randomQuestion(target: Target): String{
		val question = questionRepository.findAllByTarget(target)
		val count = question.count()
		val range = (1 until count)
		val idx: Int = range.random()
		return question[idx].question
	}
}
