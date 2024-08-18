package dev.todaystock.api.dto.info

import dev.todaystock.api.member.entity.MemberRoleType
import dev.todaystock.api.member.entity.Member
import java.util.*

class MemberResponse(
    var uuid: UUID?,
    var email: String,
    var nickname: String?,
    var name: String,
    var role: MemberRoleType,
    var deeplink: String?
) {
    companion object {
        fun fromMember(member: Member): MemberResponse = MemberResponse(
            uuid = member.uuid,
            email = member.email,
            nickname = member.nickname,
            name = member.name,
            role = member.role,
            deeplink = member.deeplink
        )
    }
}
