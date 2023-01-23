package com.smu.som.domain.question.entity

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import javax.persistence.*

//질문에 대한 정보를 담고 있는 DB table
@Entity
@Table(name = "question")
class Question(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@Enumerated(EnumType.STRING)
	var target: Target,

	var question: String,

	@Enumerated(EnumType.STRING)
	var category: Category,

	@Column(length = 1) // y or n
	var isAdult: String

) {
	//해당 DTO를 return 하기 위한 DTO로 전환하는 함수
	fun toReadQuestionDTO(): ReadQuestionDTO {
		return ReadQuestionDTO(
			id = id,
			target = target,
			question = question,
			isAdult = isAdult,
			category = category
		)
	}
	
	//해당 DTO를 질문을 새로 생성하기 위한 DTO로 전환하는 함수
	fun toCreateQuestionDTO(): CreateQuestionDTO {
		return CreateQuestionDTO(
			target = target,
			question = question,
			isAdult = isAdult,
			category = category
		)
	}
	
	//해당 DTO를 특정 질문을 update하기 위한 함수
	fun update(createQuestionDTO: CreateQuestionDTO) {
		target = createQuestionDTO.target
		question = createQuestionDTO.question
		category = createQuestionDTO.category
		isAdult = createQuestionDTO.isAdult
	}
}
