package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name="country_info")
class CountryInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid", nullable = false)
    val country: Country,

    @Column(nullable = false, length = 300)
    val title: String,

    @Column(nullable = false, length = 1000)
    val link: String,

    @Column(nullable = false, length = 100000)
    val info: String,

    @Column(nullable = false)
    var publishedDttm: LocalDateTime
): BaseEntity(), MarkerInfo {

}