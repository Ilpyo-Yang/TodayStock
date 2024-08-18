package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="country_info")
@AllArgsConstructor
class CountryInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,

    @Column(nullable = false, length = 36)
    var countryUuid: UUID,

    @Column(nullable = false, length = 1000)
    var summary: String,

    @Column(nullable = false, length = 100000)
    var info: String
): BaseEntity() {

}