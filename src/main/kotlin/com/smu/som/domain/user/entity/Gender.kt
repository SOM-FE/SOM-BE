package com.smu.som.domain.user.entity

//user의 성별을 분류하기 위한 entity
enum class Gender(
	private val initial: String
) {
	FEMALE("f"), MALE("m"), NONE("n");
}
