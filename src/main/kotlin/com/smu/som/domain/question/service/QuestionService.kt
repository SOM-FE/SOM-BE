package com.smu.som.domain.question.service

import com.smu.som.common.dto.PageResult
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.question.dto.*
import com.smu.som.domain.question.entity.Category
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.repository.QuestionRepository
import com.smu.som.domain.question.repository.UsedQuestionRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.smu.som.domain.question.repository.PlayCountRepository

@Service
@Transactional(readOnly = true)
class QuestionService(
	//사용되는 jpa repository 연결
	private val questionRepository: QuestionRepository,
	private val usedQuestionRepository: UsedQuestionRepository,
	private val playCountRepository: PlayCountRepository
) {
	//모든 question 정보를 불러옴
	fun getQuestions(): List<ReadQuestionDTO> {
		val question = questionRepository.findAll()
		return question.map { it.toReadQuestionDTO() }
	}

	//특정 category의 모든 question 정보를 불러옴
	fun getQuestions(category: Category, pageable: Pageable): PageResult<ReadQuestionDTO> {
		val questions = questionRepository.findByCategory(category, pageable)
		return PageResult.ok(questions.map { it.toReadQuestionDTO() })
	}
	
	//특정 id의 question 정보를 불러옴
	fun getQuestion(questionId: Long): ReadQuestionDTO {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		return question.toReadQuestionDTO()
	}

	//특정 id의 question 정보를 수정함
	@Transactional
	fun updateQuestion(questionId: Long, createQuestionDTO: CreateQuestionDTO): ReadQuestionDTO {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		question.update(createQuestionDTO)
		return question.toReadQuestionDTO()
	}

	//특정 id의 question 정보를 삭제함
	@Transactional
	fun deleteQuestion(questionId: Long) {
		val question = questionRepository.findByIdOrNull(questionId)
			?: throw BusinessException(ErrorCode.QUESTION_NOT_FOUND)

		questionRepository.delete(question)
	}

	//question 새로 생성
	@Transactional
	fun createQuestion(createQuestionDTO: CreateQuestionDTO): CreateQuestionDTO {
		val question = questionRepository.save(createQuestionDTO.toEntity())
		return question.toCreateQuestionDTO()
	}

	//특정 target(관계)과 성인여부에 대한 question을 목록에서 random하게 불러옴
	fun randomQuestion(target: Target, isAdult: Boolean): List<RandomQuestionDTO> {
		var question: List<RandomQuestionDTO> = if (target == Target.PARENT || target == Target.CHILD) {
			questionRepository.findByTargetInAndIsAdult(listOf(target, Target.COMMON, Target.FAMILY), "n")
		} else {
			if (isAdult) {
				questionRepository.findByTargetIn(listOf(target, Target.COMMON))
			} else {
				questionRepository.findByTargetInAndIsAdult(listOf(target, Target.COMMON), "n")
			}
		}
		return question.shuffled()
	}
	
	//특정 target(관계)과 category 및 성인여부에 대한 question을 목록에서 random하게 불러옴
	fun randomQuestion(target: Target, category: Category, isAdult: Boolean): List<RandomQuestionDTO> {
		var question: List<RandomQuestionDTO> = if (target == Target.PARENT || target == Target.CHILD) {
			questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON, Target.FAMILY), category, "n")
		} else {
			if (isAdult) {
				questionRepository.findByTargetInAndCategory(listOf(target, Target.COMMON), category)
			} else {
				questionRepository.findByTargetInAndCategoryAndIsAdult(listOf(target, Target.COMMON), category, "n")
			}
		}
		return question.shuffled()
	}

	//특정 유저의 이미 질문한/패스한 질문 id를 해당 유저 id를 갖는 row에 저장함
	@Transactional
	fun addQuestionInMyPage(kakaoId: String, getUsedQuestionDTO: GetUsedQuestionDTO): Boolean {
		try {
			for (i: Long in getUsedQuestionDTO.used)
				usedQuestionRepository.save(UsedQuestionDTO(kakaoId, i, null).toEntity())
			for (i: Long in getUsedQuestionDTO.pass)
				usedQuestionRepository.save(UsedQuestionDTO(kakaoId, null, i).toEntity())
		} catch (e: Exception) {
			e.printStackTrace()
			return false
		}
		return true
	}
	
	//특정 유저의 이미 질문한 질문 내용을 관계에 맞게 불러옴
	fun getUsedQuestion(kakaoId: String, target: Target): List<ReadQuestionDTO> {
		val usedQuestion = usedQuestionRepository.findByUserIdAndUsedIsNotNull(kakaoId)
		val ids = usedQuestion.map { q -> q.used!! }
		return getQuestions(target, ids)
	}

	//특정 유저의 패스한 질문 내용을 관계에 맞게 불러옴
	fun getPassQuestion(kakaoId: String, target: Target): List<ReadQuestionDTO> {
		val passQuestion = usedQuestionRepository.findByUserIdAndPassIsNotNull(kakaoId)
		val ids = passQuestion.map { q -> q.pass!! }
		return getQuestions(target, ids)
	}
	
	//특정 관계와 question id를 가지는 질문 목록을 불러옴
	private fun getQuestions(target: Target, ids: List<Long>): List<ReadQuestionDTO> {
		if (target == Target.FAMILY) {
			return questionRepository.findByIdIn(ids)
				.filter { q -> q.target == Target.FAMILY || q.target == Target.COMMON || q.target == Target.PARENT || q.target == Target.CHILD }
		}
		return questionRepository.findByIdIn(ids)
			.filter { q -> q.target == target || q.target == Target.COMMON }
	}

	//특정 유저의 플레이 횟수를 증가시킴. 기본값 = (0, 0, 0)으로 구성
	@Transactional
	fun increasePlayCount(kakaoId: String, target: Target): Boolean {
		var playCount: PlayCountDTO =
		if(playCountRepository.existsByUserId(kakaoId)) {
			playCountRepository.findByUserId(kakaoId)
		}
		else
			PlayCountDTO(kakaoId, 0, 0, 0)

		when(target) {
			Target.COUPLE -> playCount.couple += 1
			Target.MARRIED -> playCount.married += 1
			Target.FAMILY -> playCount.family += 1
			else -> return false
		}
		playCountRepository.deleteByUserId(kakaoId)
		playCountRepository.save(playCount.toEntity())
		return true
	}
	
	//특정 유저의 플레이 횟수를 불러옴
	fun getPlayCount(kakaoId: String): PlayCountDTO {
		return playCountRepository.findByUserId(kakaoId)
	}
}
