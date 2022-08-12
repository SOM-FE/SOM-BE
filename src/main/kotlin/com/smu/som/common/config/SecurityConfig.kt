package com.smu.som.common.config

import com.smu.som.common.jwt.filter.JwtAuthenticationFilter
import com.smu.som.common.jwt.util.JwtResolver
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
	private val jwtResolver: JwtResolver
) : WebSecurityConfigurerAdapter() {
	override fun configure(web: WebSecurity) {
		web
			.ignoring().antMatchers("/api/auth/**")
	}

	override fun configure(http: HttpSecurity) {
		http
			.httpBasic().disable()
			.csrf().disable()
			.httpBasic().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.and()
			.addFilterBefore(JwtAuthenticationFilter(jwtResolver), UsernamePasswordAuthenticationFilter::class.java)
	}
}
