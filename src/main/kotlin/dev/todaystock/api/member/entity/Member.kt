package dev.todaystock.api.member.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@Table(name="member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID?,

    @Column(nullable = false, length = 30, unique = true)
    val email: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(length = 30, unique = true)
    val nickname: String?,

    @Column(nullable = false, length = 50)
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: MemberRoleType,

    @Column(length = 300)
    var deeplink: String?
): BaseEntity() {

}