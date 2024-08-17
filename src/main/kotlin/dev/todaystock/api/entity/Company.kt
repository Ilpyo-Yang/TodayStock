package dev.todaystock.api.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="company")
@AllArgsConstructor
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, length = 300)
    var profile: String
): BaseEntity() {

}