package com.smu.som.common.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

//해당 유저의 정보를 annotation으로 가져옵니다
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
annotation class CurrentUser
