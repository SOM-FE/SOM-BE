package com.smu.som.domain.question.entity

/*
Target(관계)의 경우, user가 선택할 수 있는 관계는 총 3가지(커플, 기혼, 부모자녀)이나,
실제로 사용되는 질문의 종류는 모든 관계의 공통(Common)과, 부모->자녀(parent), 자녀->부모(child)
총 3가지의 target이 로직 상 추가됨
 */
enum class Target {
	COMMON, COUPLE, MARRIED, FAMILY, PARENT, CHILD
}
