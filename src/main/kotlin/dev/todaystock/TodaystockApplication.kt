package dev.todaystock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodaystockApplication

fun main(args: Array<String>) {
	runApplication<TodaystockApplication>(*args)
}
