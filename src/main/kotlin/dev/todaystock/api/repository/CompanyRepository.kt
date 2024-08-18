package dev.todaystock.api.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.entity.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository: JpaRepository<Company, UUID>, KotlinJdslJpqlExecutor {
    fun findByNameLike(typeName: String): Company?
}