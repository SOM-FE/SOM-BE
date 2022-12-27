package com.smu.som.domain.question.repository

import com.smu.som.domain.question.entity.UsedQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface UsedQuestionRepository : JpaRepository<UsedQuestion, Long> {
}
