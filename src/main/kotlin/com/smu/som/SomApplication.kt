package com.smu.som

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SomApplication

fun main(args: Array<String>) {
	runApplication<SomApplication>(*args)
}
