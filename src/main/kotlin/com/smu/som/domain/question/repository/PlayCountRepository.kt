package com.smu.som.domain.question.repository

import com.smu.som.domain.question.dto.PlayCountDTO
import com.smu.som.domain.question.entity.PlayCount
import org.springframework.data.jpa.repository.JpaRepository

//JPA Repository by PlayCount entity
interface PlayCountRepository : JpaRepository<PlayCount, Long> {
	//해당 userId가 존재하는지에 대한 여부 검사
	fun existsByUserId(userId: String): Boolean
	//해당 userId의 row를 가져옴
	fun findByUserId(userId: String): PlayCountDTO
	//해당 userId의 row 삭제
	fun deleteByUserId(userId: String)
}
