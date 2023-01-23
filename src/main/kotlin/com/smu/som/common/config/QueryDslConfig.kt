package com.smu.som.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//query dsl을 사용하기 위한 정의
@Configuration
class QueryDslConfig {
 
    @PersistenceContext
    lateinit var em: EntityManager
 
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}
