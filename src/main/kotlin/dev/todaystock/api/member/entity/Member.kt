package dev.todaystock.api.member.entity

import dev.todaystock.api.common.entity.BaseEntity
import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name="member")
@AllArgsConstructor
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID?,

    @Column(nullable = false, length = 30, unique = true)
    var email: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(length = 30, unique = true)
    var nickname: String?,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: MemberRoleType,

    @Column(length = 300)
    var deeplink: String?
): BaseEntity() {

}