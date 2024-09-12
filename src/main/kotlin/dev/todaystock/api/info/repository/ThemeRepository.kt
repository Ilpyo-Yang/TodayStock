package dev.todaystock.api.info.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.info.entity.Theme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ThemeRepository: JpaRepository<Theme, UUID>, KotlinJdslJpqlExecutor {
    fun findByName(name: String): Optional<Theme>
    fun findTopByOrderByCreatedDateDesc(): Theme
}