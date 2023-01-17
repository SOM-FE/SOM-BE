package com.smu.som.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

//rest api 통신을 위한 rest template 정의
@Configuration
class RestTemplateConfig {
	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}
}
