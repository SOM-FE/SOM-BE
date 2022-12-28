package com.smu.som.controller.api

import com.smu.som.domain.question.dto.GetUsedQuestionDTO
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
		return try {
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

	@PostMapping("/question/{kakaoid}")
	fun myQuestion(
		@PathVariable(name = "kakaoid") kakaoId: String,
		@RequestBody getUsedQuestionDTO: GetUsedQuestionDTO
	): ResponseEntity<Any> {
		return try {
			ResponseEntity.ok().body(questionService.addQuestionInMyPage(kakaoId, getUsedQuestionDTO))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}

	@GetMapping("/question/{kakaoid}/{target}/used")
	fun getUsedQuestion(
		@PathVariable(name = "kakaoid") kakaoId: String,
		@PathVariable(name = "target") target: String
	): ResponseEntity<Any> {
		return try {
			ResponseEntity.ok().body(questionService.getUsedQuestion(kakaoId, Target.valueOf(target.uppercase())))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}

	@GetMapping("/question/{kakaoid}/{target}/pass")
	fun getPassQuestion(
		@PathVariable(name = "kakaoid") kakaoId: String,
		@PathVariable(name = "target") target: String
	): ResponseEntity<Any> {
		return try {
			ResponseEntity.ok().body(questionService.getPassQuestion(kakaoId, Target.valueOf(target.uppercase())))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
