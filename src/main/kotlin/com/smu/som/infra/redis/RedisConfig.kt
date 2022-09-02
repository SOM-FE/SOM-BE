package com.smu.som.infra.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Profile("local")
@Configuration
class RedisConfig(
	@Value("\${spring.redis.host}") val redisHost: String,
	@Value("\${spring.redis.port}") val redisPort: Int
) {
	@Bean
	fun redisConnectionFactory(): RedisConnectionFactory {
		return LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	fun redisTemplate(): RedisTemplate<String, Any> {
		val redisTemplate = RedisTemplate<String, Any>()
		redisTemplate.setConnectionFactory(redisConnectionFactory())
		redisTemplate.keySerializer = StringRedisSerializer()
		redisTemplate.valueSerializer = StringRedisSerializer()
		return redisTemplate
	}
}
