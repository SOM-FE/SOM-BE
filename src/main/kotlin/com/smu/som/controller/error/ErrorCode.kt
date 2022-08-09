package com.smu.som.controller.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val status: HttpStatus,
	val message: String
) {
	//Common
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다."),
	QUESTION_NOT_FOUNT(HttpStatus.NOT_FOUND, "요청하신 내역을 찾을 수 없습니다.")
}
