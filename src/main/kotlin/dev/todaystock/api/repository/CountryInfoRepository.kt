package dev.todaystock.api.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.entity.CountryInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CountryInfoRepository: JpaRepository<CountryInfo, UUID>, KotlinJdslJpqlExecutor {
}