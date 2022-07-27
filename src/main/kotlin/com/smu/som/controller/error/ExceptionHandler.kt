package com.smu.som.controller.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@RestControllerAdvice
class ExceptionHandler {
	@ExceptionHandler(BusinessException::class)
	fun handleBaseException(e: BusinessException): ResponseEntity<ErrorResponse> {
		return ResponseEntity.status(e.errorCode.status).body(ErrorResponse.of(e.errorCode))
	}

	@ExceptionHandler(RuntimeException::class)
	fun handleUnexpectedException(e: RuntimeException): ResponseEntity<ErrorResponse> {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR))
	}
}
