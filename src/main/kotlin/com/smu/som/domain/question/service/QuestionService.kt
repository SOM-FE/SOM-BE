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

	// List<Question>은 non-null 이라 Exception 을 발생시키지 않음 -> throw BusinessException 제거
	fun randomQuestion(target: Target, category: Category, isAdult: Boolean): List<Question> {
		println(isAdult)
		var familyQuestion: List<Question> = emptyList()
		var commonQuestion: List<Question> = emptyList()
		var question: List<Question> = if(isAdult) {
			commonQuestion = questionRepository.findByTargetAndCategory(Target.COMMON, category)
			questionRepository.findByTargetAndCategory(target, category)
		} else {
			commonQuestion = questionRepository.findByTargetAndCategoryAndIsAdult(Target.COMMON, category, "n")
			questionRepository.findByTargetAndCategoryAndIsAdult(target, category, "n")
		}
		if(target == Target.PARENT || target == Target.CHILD) {
			familyQuestion = questionRepository.findByTargetAndCategory(Target.FAMILY, category)
		}
		var questionList: MutableList<Question> = (commonQuestion + question + familyQuestion).map { it.question } as MutableList<Question>
		return questionList.shuffled()
	}
}
