package com.smu.som.controller.api

import com.smu.som.common.dto.PageResult
import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.dto.ReadQuestionDTO
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.service.QuestionService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/questions")
class AdminController(
	private val questionService: QuestionService
) {
	//질문 등록 api
	@PostMapping
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any> {
		questionService.createQuestion(createQuestionDTO)
		return ResponseEntity.ok().body(null)
	}
	//질문목록 조회 api
	@GetMapping
	fun getQuestions(@RequestParam(name = "category") category: String,
					 @PageableDefault(size = 10) pageable: Pageable): PageResult<ReadQuestionDTO> {
		return questionService.getQuestions(Category.valueOf(category.uppercase()), pageable)
	}
	//특정 질문 조회 api
	@GetMapping("/{questionId}")
	fun getQuestion(@PathVariable(name = "questionId") questionId: Long): ReadQuestionDTO {
		return questionService.getQuestion(questionId)
	}
	//특정 질문 수정 api
	@PutMapping("/{questionId}")
	fun updateQuestion(@PathVariable questionId: Long,
					   @RequestBody createQuestionDTO: CreateQuestionDTO): ReadQuestionDTO {
		return questionService.updateQuestion(questionId, createQuestionDTO)
	}
	//특정 질문 삭제 api
	@DeleteMapping("/{questionId}")
	fun deleteQuestion(@PathVariable questionId: Long): ResponseEntity<Nothing> {
		questionService.deleteQuestion(questionId)
		return ResponseEntity.ok().body(null)
	}
}
