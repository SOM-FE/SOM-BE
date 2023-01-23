package com.smu.som.common.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

//Log를 찍기 위한 logger 정의
class Logger {
	companion object {
		private val log : Logger get() = LoggerFactory.getLogger(this::class.java)
		fun error(message: String?) {
			log.error(message)
		}

		fun info(message: String?) {
			log.info(message)
		}

		fun trace(message: String?) {
			log.trace(message)
		}
	}
}
