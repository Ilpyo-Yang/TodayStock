package dev.todaystock.api.entity

import dev.todaystock.api.entity.type.UserRoleType
import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="user")
@AllArgsConstructor
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(length = 30)
    var nickname: String?,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false)
    var role: UserRoleType,

    @Column(length = 300)
    var deeplink: String?
): BaseEntity() {

}