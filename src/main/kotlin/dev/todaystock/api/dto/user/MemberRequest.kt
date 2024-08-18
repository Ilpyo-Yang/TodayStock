package dev.todaystock.api.dto.info

import dev.todaystock.api.entity.*
import dev.todaystock.api.entity.type.UserRoleType
import java.util.*

class MemberRequest(
    var uuid: UUID?,
    var email: String,
    var password: String,
    var nickname: String?,
    var name: String,
    var role: UserRoleType,
    var deeplink: String?
) {
    companion object {
        fun toCompanyInfo(memberRequest: MemberRequest): Member = Member(
            uuid = memberRequest.uuid,
            email = memberRequest.email,
            password = memberRequest.password,
            nickname = memberRequest.nickname?.let { randomUsername() },
            name = memberRequest.name,
            role = memberRequest.role,
            deeplink = memberRequest.deeplink
        )

        fun randomUsername(): String {
            // todo
            return ""
        }
    }


}
