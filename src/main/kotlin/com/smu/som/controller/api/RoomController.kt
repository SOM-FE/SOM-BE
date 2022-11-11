package com.smu.som.controller.api

import com.smu.som.domain.chat.repository.ChatRoomRepository
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@RestController
@RequestMapping("/chat")
class RoomController(
	private val repository: ChatRoomRepository
) {
	@GetMapping("/rooms")
	fun rooms(): ModelAndView {
		var mv = ModelAndView("chat/rooms")

		mv.addObject("list", repository.findAllRooms())
		return mv;
	}

	@PostMapping("/room")
	fun create(@RequestParam name: String, rttr: RedirectAttributes): String {
		rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name))
		return "redirect:/chat/rooms"
	}

	@GetMapping("/room")
	fun getRoom(roomId: String, model: Model){
		model.addAttribute("room", repository.findRoomById(roomId))
	}
}
