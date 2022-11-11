package com.smu.som.domain.chat.repository

import com.smu.som.domain.chat.dto.ChatRoomDTO
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

@Repository
class ChatRoomRepository {
	private lateinit var chatRoomDTOMap: LinkedHashMap<String, ChatRoomDTO>

	fun findAllRooms(): List<ChatRoomDTO> {
		var result: List<ChatRoomDTO> = ArrayList(chatRoomDTOMap.values)
		Collections.reverse(result)
		return result
	}

	fun findRoomById(id: String): ChatRoomDTO? {
		return chatRoomDTOMap[id]
	}

	fun createChatRoomDTO(name: String): ChatRoomDTO {
		lateinit var room: ChatRoomDTO
		room.create(name)
		chatRoomDTOMap[room.roomId] = room
		return room
	}
}
