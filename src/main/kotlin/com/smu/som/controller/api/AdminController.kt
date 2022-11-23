package com.smu.som.controller.api

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.service.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
	private val questionService: QuestionService
) {
	@PostMapping("/question")
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any> {
		return try {
			questionService.createQuestion(createQuestionDTO)
			ResponseEntity.ok().body(null)
		} catch (e: Exception) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
