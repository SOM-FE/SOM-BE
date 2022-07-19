package com.smu.som.service

import com.smu.som.dto.CreateQuestionDTO
import com.smu.som.dto.ReadQuestionDTO
import com.smu.som.entities.QuestionEntity
import com.smu.som.entities.Target
import com.smu.som.repositories.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class QuestionService {
	@Autowired
	lateinit var questionRepository: QuestionRepository

	//모든 질문 조회
	fun getQuestions(): List<ReadQuestionDTO>{
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	//질문 등록
	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO{
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	//랜덤 api
	fun randomQuestion(target: Target): String{
		val count = questionRepository.findAllByTarget(target).count()
		val range = (1..count-1)
		val idx: Int = range.random()
		val question = questionRepository.findAllByTarget(target)
		return question[idx].question
	}
}
