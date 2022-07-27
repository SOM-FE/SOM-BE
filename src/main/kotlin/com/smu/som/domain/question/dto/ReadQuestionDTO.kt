package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entities.Target;

class ReadQuestionDTO(
	val id: Long? = null,
	val target: Target,
	val category: String,
	val question: String
)
