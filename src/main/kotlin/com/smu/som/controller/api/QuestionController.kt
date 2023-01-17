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
	//모든 질문 목록을 가져와 반환하는 api
	@GetMapping("/questions", produces = ["application/json"])
	fun getQuestions(): ResponseEntity<Any> {
		return ResponseEntity.ok().body(questionService.getQuestions())
	}
	//특정 관계별 질문 목록을 randomizing 하게 가져오는 api
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
	//특정 관계 및 카테고리별 질문 목록을 randomizing 하게 가져오는 api
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
	//특정 게임에 이미 받은/패스한 질문을 등록하는 api
	@PostMapping("/question/{kakaoid}/{target}")
	fun myQuestion(
		@PathVariable(name = "kakaoid") kakaoId: String,
		@PathVariable(name = "target") target: String,
		@RequestBody getUsedQuestionDTO: GetUsedQuestionDTO
	): ResponseEntity<Any> {
		return try {
			questionService.increasePlayCount(kakaoId, Target.valueOf(target.uppercase()))
			ResponseEntity.ok().body(questionService.addQuestionInMyPage(kakaoId, getUsedQuestionDTO))
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
	//특정 유저가 플레이했던 관계별 받은 질문을 가져오는 api
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
	//특정 유저가 플레이했던 관계별 패스한 질문을 가져오는 api
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
	//특정 유저의 플레이 횟수를 가져오는 api
	@GetMapping("/question/playcount/{kakaoid}")
	fun getPlayCount(
		@PathVariable(name = "kakaoid") kakaoId: String
	): ResponseEntity<Any> {
		return ResponseEntity.ok().body(questionService.getPlayCount(kakaoId))
	}
}
