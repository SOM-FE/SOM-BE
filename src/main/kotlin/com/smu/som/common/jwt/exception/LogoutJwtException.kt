package com.smu.som.common.jwt.exception

import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode

//logout과 관련된 exception 정의
class LogoutJwtException : BusinessException(ErrorCode.LOGOUT_TOKEN)
