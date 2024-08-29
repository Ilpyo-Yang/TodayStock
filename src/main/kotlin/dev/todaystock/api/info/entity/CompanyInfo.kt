package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="company_info")
class CompanyInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_uuid", nullable = false)
    val company: Company,

    @Column(nullable = false, length = 300)
    val title: String,

    @Column(nullable = false, length = 1000)
    val link: String,

    @Column(nullable = false, length = 100000)
    val info: String
): BaseEntity() {

}