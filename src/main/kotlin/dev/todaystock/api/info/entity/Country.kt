package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@Table(name="country")
class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 300)
    val profile: String
): BaseEntity() {

}