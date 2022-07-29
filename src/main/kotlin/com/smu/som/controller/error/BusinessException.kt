package com.smu.som.controller.error

open class BusinessException(
	val errorCode: ErrorCode
) : RuntimeException()
