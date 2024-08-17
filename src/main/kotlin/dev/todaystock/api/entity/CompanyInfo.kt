package dev.todaystock.api.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="company_info")
@AllArgsConstructor
class CompanyInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,

    @Column(nullable = false, length = 36)
    var companyUuid: UUID,

    @Column(nullable = false, length = 1000)
    var summary: String,

    @Column(nullable = false, length = 100000)
    var description: String
): BaseEntity() {

}