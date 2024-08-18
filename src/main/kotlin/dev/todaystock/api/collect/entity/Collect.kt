package dev.todaystock.api.collect.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="collect")
@AllArgsConstructor
class Collect(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,
    var userUuid: UUID,
    var type: String,
    var relatedInfoUuid: UUID,
    var summary: String
): BaseEntity() {

}