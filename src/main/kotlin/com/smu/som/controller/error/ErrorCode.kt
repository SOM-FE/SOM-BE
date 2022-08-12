package com.smu.som.controller.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
	val status: HttpStatus,
	val message: String
) {
	//Common
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다."),
	QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 내역을 찾을 수 없습니다."),

	//Auth
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
	USER_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
	EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "만료된 엑세스 토큰입니다."),
	INVALID_JWT(HttpStatus.UNAUTHORIZED, "권한이 없는 회원의 접근입니다.")
}
