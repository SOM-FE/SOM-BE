package com.smu.som.domain.chat.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatMessage(
	@JsonProperty("type")
	var type: MessageType,

	@JsonProperty("content")
	var content: String?,

	@JsonProperty("sender")
	var sender: String
)
