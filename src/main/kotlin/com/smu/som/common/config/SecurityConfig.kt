package com.smu.som.common.config

import com.smu.som.common.jwt.filter.JwtAuthenticationFilter
import com.smu.som.common.jwt.util.JwtResolver
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
	private val jwtResolver: JwtResolver
) {
	@Bean
	fun webSecurityCustomizer(): WebSecurityCustomizer? {
		return WebSecurityCustomizer { web: WebSecurity ->
			web.ignoring().antMatchers("/api/auth/signin", "/api/auth/refresh", "/api/auth/signup")
		}
	}

	@Bean
	fun filterChain(http: HttpSecurity?): SecurityFilterChain {
		http!!
			.httpBasic().disable()
			.csrf().disable()
			.exceptionHandling()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/api/auth/signin", "/api/auth/refresh", "/api/auth/signup").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(JwtAuthenticationFilter(jwtResolver), UsernamePasswordAuthenticationFilter::class.java)
		return http.build()
	}
}
