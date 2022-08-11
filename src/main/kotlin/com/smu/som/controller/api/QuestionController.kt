package com.smu.som.controller.api

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.service.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

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

	@GetMapping("/question/{target}")
	fun randomQuestion(@PathVariable(name = "target") target: String, session: HttpSession): ResponseEntity<Any> {
		return try {
			val targetName: Target = Target.valueOf(target.uppercase())
			if(session.getAttribute("index") == null || session.getAttribute("index") == -1) {
				var questionNoList = questionService.randomQuestion(targetName)
				session.setAttribute("question", questionNoList)
				session.setAttribute("index", 0)
			}

			val idx: Int = session.getAttribute("index") as Int
			val questionList: MutableList<Long> = session.getAttribute("question") as MutableList<Long>
			val question: String = questionService.getQuestion(questionList[idx])
			var value = -1
			if(questionList.size-1 > idx) {
				value = idx + 1
			}
			session.setAttribute("index", value)

			ResponseEntity.ok().body(question)
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}

	@GetMapping("/question/finish")
	fun questionDelete(session: HttpSession): ResponseEntity<Any> {
		return try {
			session.removeAttribute("index")
			session.removeAttribute("question")
			ResponseEntity.ok().body(null)
		} catch (e: Exception) {
			e.printStackTrace()
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
