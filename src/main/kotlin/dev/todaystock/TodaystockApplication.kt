package dev.todaystock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TodaystockApplication

fun main(args: Array<String>) {
	runApplication<TodaystockApplication>(*args)
}
