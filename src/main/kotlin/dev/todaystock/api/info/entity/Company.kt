package dev.todaystock.api.info.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="company")
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false, length = 30)
    val ticker: String,

    @Column(nullable = false, length = 300)
    val profile: String,

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    val companyInfoList: MutableList<CompanyInfo>? = mutableListOf()

): BaseEntity() {

}