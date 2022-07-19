package com.smu.som.dto

import com.smu.som.entities.Target;

data class ReadQuestionDTO(
	val id: Long? = null,
	val target: Target,
	val category: String,
	val question: String
) {
}
