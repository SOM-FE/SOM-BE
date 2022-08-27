package com.smu.som.common.jwt.exception

import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode

class LogoutJwtException : BusinessException(ErrorCode.LOGOUT_TOKEN)
