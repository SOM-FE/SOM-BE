package com.smu.som.controller.api

import com.smu.som.domain.chat.dto.ChatMessageDTO
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class StompChatController(
	private val template: SimpMessagingTemplate
) {
	@MessageMapping("/chat/enter")
	fun enter(message: ChatMessageDTO) {
		message.message = message.writer + "님이 채팅방에 참여하였습니다."
		template.convertAndSend("/sub/chat/room/" + message.roomId, message)
	}

	@MessageMapping("/chat/message")
	fun message(message: ChatMessageDTO) {
		template.convertAndSend("/sub/chat/room/" + message.roomId, message)
	}
}
