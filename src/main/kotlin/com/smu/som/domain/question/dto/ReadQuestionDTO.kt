package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entities.Target;

class ReadQuestionDTO(
	var id: Long,
	var target: Target,
	var question: String
)
