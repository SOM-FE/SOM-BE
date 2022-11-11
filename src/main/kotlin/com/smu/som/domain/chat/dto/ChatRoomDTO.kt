package com.smu.som.domain.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.socket.WebSocketSession
import java.util.*

class ChatRoomDTO(
	var roomId: String,
	var name: String,
	var sessions: Set<WebSocketSession>?
) {
	fun create(name: String): ChatRoomDTO {
		lateinit var room: ChatRoomDTO

		room.roomId = UUID.randomUUID().toString()
		room.name = name
		return room
	}
}
