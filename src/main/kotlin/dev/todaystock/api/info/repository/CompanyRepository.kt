package dev.todaystock.api.info.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.info.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository: JpaRepository<Company, UUID>, KotlinJdslJpqlExecutor {
    fun findByName(name: String): Optional<Company>
    fun findByTicker(ticker: String): Optional<Company>
    fun findTopByOrderByCreatedDateDesc(): Company
}