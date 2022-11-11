package com.smu.som.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.*

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {
	override fun registerStompEndpoints(registry: StompEndpointRegistry) {
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*")
			.withSockJS()
	}

	override fun configureMessageBroker(config: MessageBrokerRegistry) {
		config.setApplicationDestinationPrefixes("/app")
		config.enableSimpleBroker("/topic")
	}
}
