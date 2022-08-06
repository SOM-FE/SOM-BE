package com.smu.som.controller.api

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.entities.Target
import com.smu.som.domain.question.service.QuestionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession

@RestController
class QuestionController(
	private val questionService: QuestionService
) {
	@GetMapping("/questions", produces = ["application/json"])
	fun getQuestions(): ResponseEntity<Any> {
		return ResponseEntity.ok().body(questionService.getQuestions())
	}

	@PostMapping("/question")
	fun createQuestion(@RequestBody createQuestionDTO: CreateQuestionDTO): ResponseEntity<Any> {
		questionService.createQuestion(createQuestionDTO)
		return ResponseEntity.ok().body(null)
	}

	@GetMapping("/question/{target}")
	fun randomQuestion(@PathVariable(name = "target") target: String, session: HttpSession): ResponseEntity<Any> {
		try {
			val targetName: Target = Target.valueOf(target.uppercase())
			if(session.getAttribute("index") == null || session.getAttribute("index") == -1) {
				var questionNoList = questionService.randomQuestion(targetName)
				session.setAttribute("question", questionNoList)
				session.setAttribute("index", 0)
			}

			var idx: Int = session.getAttribute("index") as Int
			var questionList: MutableList<Long> = session.getAttribute("question") as MutableList<Long>
			var question: String = questionService.getQuestion(questionList[idx])

			if(questionList.size-1 > idx)	session.setAttribute("index", idx+1)
			else	session.setAttribute("index", -1)

			return ResponseEntity.ok().body(question)
		} catch (e: Exception) {
			e.printStackTrace()
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}

	@GetMapping("/question/finish")
	fun questionDelete(session: HttpSession): ResponseEntity<Any> {
		try {
			session.removeAttribute("index")
			session.removeAttribute("question")
			return ResponseEntity.ok().body(null)
		} catch (e: Exception) {
			e.printStackTrace()
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
		}
	}
}
