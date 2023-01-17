package com.smu.som.controller.error

//Error response시, 해당 error 번호(Http 상태 코드)와 error message 반환
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
