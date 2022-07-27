package com.smu.som.dto

import com.smu.som.entities.Target;

class ReadQuestionDTO(
	val id: Long? = null,
	val target: Target,
	val category: String,
	val question: String
)
