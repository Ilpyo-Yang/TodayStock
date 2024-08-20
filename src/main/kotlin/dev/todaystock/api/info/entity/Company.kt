package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@Table(name="company")
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 300)
    val profile: String
): BaseEntity() {

}