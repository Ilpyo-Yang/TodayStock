package dev.todaystock.api.info.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="theme_info")
class ThemeInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_uuid", nullable = false)
    val theme: Theme,

    @Column(nullable = false, length = 300)
    val title: String,

    @Column(nullable = false, length = 1000)
    val link: String,

    @Column(nullable = false, length = 100000)
    val info: String
): BaseEntity() {

}