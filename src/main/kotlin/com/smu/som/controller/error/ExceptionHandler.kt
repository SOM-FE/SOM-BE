package com.smu.som.controller.error

import com.smu.som.common.util.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@RestControllerAdvice
class ExceptionHandler {
	@ExceptionHandler(BusinessException::class)
	fun handleBaseException(e: BusinessException): ResponseEntity<ErrorResponse> {
		Logger.error(e.errorCode.message)
		return ResponseEntity.status(e.errorCode.status).body(ErrorResponse.of(e.errorCode))
	}

	@ExceptionHandler(RuntimeException::class)
	fun handleUnexpectedException(e: RuntimeException): ResponseEntity<ErrorResponse> {
		Logger.error(e.message)
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR))
	}
}
