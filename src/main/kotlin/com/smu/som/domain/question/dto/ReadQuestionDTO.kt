package com.smu.som.domain.question.dto

import com.smu.som.domain.question.entity.Target;

class ReadQuestionDTO(
	var id: Long,
	var target: Target,
	var question: String,
	var adult_status: String
)
