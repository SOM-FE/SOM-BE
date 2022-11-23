package com.smu.som.controller.api

import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.service.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class QuestionController(
	private val questionService: QuestionService
) {
	@GetMapping("/questions", produces = ["application/json"])
	fun getQuestions(): ResponseEntity<Any> {
		return ResponseEntity.ok().body(questionService.getQuestions())
	}

	@GetMapping("/question/{target}")
	fun randomTargetQuestion(
		@PathVariable(name = "target") target: String,
		@RequestParam("isAdult") adult: String
	): ResponseEntity<Any> {
		return try{
			val targetName: Target = Target.valueOf(target.uppercase())
			val isAdult: Boolean = adult == "y" || adult == "Y"
			ResponseEntity.ok().body(questionService.randomQuestion(targetName, isAdult))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}

	@GetMapping("/question/{target}/{category}")
	fun randomQuestion(
		@PathVariable(name = "target") target: String,
		@PathVariable(name = "category") category: String,
		@RequestParam("isAdult") adult: String
	): ResponseEntity<Any> {
		return try {
			val targetName: Target = Target.valueOf(target.uppercase())
			val categoryName: Category = Category.valueOf(category.uppercase())
			val isAdult: Boolean = adult == "y" || adult == "Y"
			ResponseEntity.ok().body(questionService.randomQuestion(targetName, categoryName, isAdult))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
