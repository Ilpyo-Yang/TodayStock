package dev.todaystock.api.collect.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="collect")
class Collect(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,
    val userUuid: UUID,
    val type: String,
    val relatedInfoUuid: UUID,
    val summary: String
): BaseEntity() {

}