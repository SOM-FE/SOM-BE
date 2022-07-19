package com.smu.som.controller

import com.smu.som.dto.CreateQuestionDTO
import com.smu.som.entities.Target
import com.smu.som.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class QuestionController {
	@Autowired
	private lateinit var questionService: QuestionService

	//모든 질문 조회
	@GetMapping("/questions", produces= ["application/json"])
	fun getQuestions(): ResponseEntity<Any>{
		return ResponseEntity.ok().body(questionService.getQuestions())
	}

	//질문 등록
	@PostMapping("/question")
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any>{
		questionService.createQuestion(createQuestionDTO)
		return ResponseEntity.ok().body(true)
	}

	//랜덤 api
	@GetMapping("/question/{target}")
	fun randomQuestion(@PathVariable(name = "target") target: Target ): ResponseEntity<Any>{
		return ResponseEntity.ok().body(questionService.randomQuestion(target));
	}
}
