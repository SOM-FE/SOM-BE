package com.smu.som.domain.chat.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ChatMessageDTO(
	var roomId: String,
	var writer: String,
	var message: String
) {

}
