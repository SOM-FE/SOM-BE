package com.smu.som

import com.smu.som.domain.question.dto.CreateQuestionDTO
import com.smu.som.domain.question.entity.Target
import com.smu.som.domain.question.repository.QuestionRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class QuestionRepositoryTest (
	val questionRepository: QuestionRepository
	){

	@Test
	fun questionTest(){
		//when
		val count = questionRepository.count()
		//then
		Assertions.assertThat(count).isEqualTo(0)
	}

	@Test
	fun insertTest(){
		//given
		val question = CreateQuestionDTO(target = Target.COUPLE, question = "남자로 사는 것이 힘들 때는 언제인가요?").toEntity()
		questionRepository.save(question)
	}
}
