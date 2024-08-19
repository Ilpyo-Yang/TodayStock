package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="company_info")
class CompanyInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 36)
    val companyUuid: UUID,

    @Column(nullable = false, length = 1000)
    val summary: String,

    @Column(nullable = false, length = 100000)
    val info: String
): BaseEntity() {

}