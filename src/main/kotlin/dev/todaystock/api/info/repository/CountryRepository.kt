package dev.todaystock.api.info.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CountryRepository: JpaRepository<Country, UUID>, KotlinJdslJpqlExecutor {
    fun findByName(typeName: String): Country?
    fun findTopByOrderByCreatedDateDesc(): Country
}