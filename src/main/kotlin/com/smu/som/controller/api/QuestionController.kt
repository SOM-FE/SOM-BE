package com.smu.som.controller.api

import com.smu.som.common.annotation.CurrentUser
import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.service.QuestionService
import com.smu.som.domain.user.entity.User
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

	@PostMapping("/question")
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any> {
		return try{
			questionService.createQuestion(createQuestionDTO)
			ResponseEntity.ok().body(null)
		} catch (e: Exception){
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}

	}

	@GetMapping("/question/{target}/{category}")
	fun randomQuestion(
		@PathVariable(name = "target") target: String,
		@PathVariable(name = "category") category: String,
		@CurrentUser user: User
	): ResponseEntity<Any> {
		return try {
			val targetName: Target = Target.valueOf(target.uppercase())
			val categoryName: Category = Category.valueOf(category.uppercase())
			val isAdult: Boolean = !user.ageRange?.get(0)?.equals("0")!! || !user.ageRange?.get(0)?.equals("1")!!
			ResponseEntity.ok().body(questionService.randomQuestion(targetName, categoryName, isAdult))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
