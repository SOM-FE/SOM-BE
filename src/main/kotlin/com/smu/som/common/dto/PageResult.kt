package com.smu.som.common.dto

import org.springframework.data.domain.Page

//특정 DTO를 page화 시켜 return 하기 위해 page안에 담길 값 정의
class PageResult<T>(
	data: Page<T>
) {
	val currentPage: Int
	val totalPage: Int
	val pageSize: Int
	val totalElements: Long
	val content: List<T>

	init {
		currentPage = data.pageable.pageNumber
		pageSize = data.pageable.pageSize
		totalPage = data.totalPages
		totalElements = data.totalElements
		content = data.content
	}

	companion object {
		fun <T> ok(data: Page<T>): PageResult<T> {
			return PageResult(data)
		}
	}
}
