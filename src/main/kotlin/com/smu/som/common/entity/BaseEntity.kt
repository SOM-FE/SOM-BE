package com.smu.som.common.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

//기본적인 entity의 생성 및 최종수정일시를 기록하기 위한 정의
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {
	@CreatedDate
	@Column(name = "created_at", updatable = false)
	var createdAt: LocalDateTime = LocalDateTime.now()

	@LastModifiedDate
	@Column(name = "updated_at")
	var updatedAt: LocalDateTime = LocalDateTime.now()
}
