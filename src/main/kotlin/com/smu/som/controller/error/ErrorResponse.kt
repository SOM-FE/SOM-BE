package com.smu.som.controller.error

class ErrorResponse(
	val status: Int,
	val message: String
) {
	companion object {
		fun of(e: ErrorCode): ErrorResponse {
			return ErrorResponse(e.status.value(), e.message)
		}
	}
}
