package dev.todaystock.api.info.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
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
    val profile: String,

    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true)
    val companyInfoList: MutableList<CountryInfo>? = mutableListOf()

): BaseEntity() {

}