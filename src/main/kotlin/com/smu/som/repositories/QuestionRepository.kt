package com.smu.som.repositories

import com.smu.som.entities.Question
import com.smu.som.entities.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository: CrudRepository<Question, Long> {
	fun findAllBy(): List<Question>
	fun findAllByTarget(target: Target) : List<Question>
}
