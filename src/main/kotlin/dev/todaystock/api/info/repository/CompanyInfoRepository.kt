package dev.todaystock.api.info.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import dev.todaystock.api.info.entity.CompanyInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyInfoRepository: JpaRepository<CompanyInfo, UUID>, KotlinJdslJpqlExecutor {
    fun findByUuid(infoUuid: UUID): List<CompanyInfo>
    fun findByCompanyUuid(infoTypeUuid: UUID): List<CompanyInfo>
}