package com.smu.som.repositories

import com.smu.som.entities.QuestionEntity
import com.smu.som.entities.Target
import org.springframework.data.repository.CrudRepository

interface QuestionRepository: CrudRepository<QuestionEntity, Long> {
	fun findAllBy(): List<QuestionEntity>
	fun findAllByTarget(target: Target) : List<QuestionEntity>
}
