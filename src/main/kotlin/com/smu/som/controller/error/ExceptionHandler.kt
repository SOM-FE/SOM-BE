package com.smu.som.controller.error

import com.smu.som.common.util.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

//Exception handler 정의
@RestControllerAdvice
class ExceptionHandler {
	//기본(base) Exception 방식 정의
	@ExceptionHandler(BusinessException::class)
	fun handleBaseException(e: BusinessException): ResponseEntity<ErrorResponse> {
		Logger.error(e.errorCode.message)
		return ResponseEntity.status(e.errorCode.status).body(ErrorResponse.of(e.errorCode))
	}

	//Exception 발생 시 error log를 찍은 후 서버 에러 return
	@ExceptionHandler(RuntimeException::class)
	fun handleUnexpectedException(e: RuntimeException): ResponseEntity<ErrorResponse> {
		Logger.error(e.message)
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR))
	}
}
