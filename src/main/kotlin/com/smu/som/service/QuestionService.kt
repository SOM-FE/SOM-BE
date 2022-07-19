package com.smu.som.service

import com.smu.som.dto.CreateQuestionDTO
import com.smu.som.dto.ReadQuestionDTO
import com.smu.som.entities.Target
import com.smu.som.repositories.QuestionRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QuestionService(private val questionRepository: QuestionRepository) {

	fun getQuestions(): List<ReadQuestionDTO>{
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO{
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	fun randomQuestion(target: Target): String{
		val question = questionRepository.findAllByTarget(target)
		val count = question.count()
		val range = (1 until count)
		val idx: Int = range.random()
		return question[idx].question
	}
}
