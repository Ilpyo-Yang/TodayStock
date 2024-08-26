package dev.todaystock.api.info.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="theme")
class Theme(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 300)
    val profile: String,

    @JsonIgnore
    @OneToMany(mappedBy = "theme", cascade = [CascadeType.ALL], orphanRemoval = true)
    val themeInfoList: MutableList<ThemeInfo>? = mutableListOf()

): BaseEntity() {

}