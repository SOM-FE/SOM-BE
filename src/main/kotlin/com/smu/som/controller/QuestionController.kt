package com.smu.som.controller

import com.smu.som.dto.CreateQuestionDTO
import com.smu.som.entities.Target
import com.smu.som.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class QuestionController(
	private val questionService: QuestionService
	) {

	@GetMapping("/questions", produces= ["application/json"])
	fun getQuestions(): ResponseEntity<Any>{
		return ResponseEntity.ok().body(questionService.getQuestions())
	}

	@PostMapping("/question")
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any>{
		questionService.createQuestion(createQuestionDTO)
		return ResponseEntity.ok().body(true)
	}

	@GetMapping("/question/{target}")
	fun randomQuestion(@PathVariable(name = "target") target: String ): ResponseEntity<Any>{
		var targetName: Target = Target.valueOf(target.uppercase())
		return ResponseEntity.ok().body(questionService.randomQuestion(targetName));
	}
}
