package com.smu.som.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//ObjectMapper에 대한 setting
@Configuration
class ObjectMapperConfig {
	//bean 등록
	@Bean
	fun objectMapper(): ObjectMapper {
		return ObjectMapper()
	}
}
