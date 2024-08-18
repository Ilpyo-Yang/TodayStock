package dev.todaystock.api.info.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.info.entity.ThemeInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ThemeInfoRepository: JpaRepository<ThemeInfo, UUID>, KotlinJdslJpqlExecutor {
    fun findByUuid(infoUuid: UUID): List<ThemeInfo>
}