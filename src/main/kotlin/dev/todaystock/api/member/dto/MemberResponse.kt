package dev.todaystock.api.member.dto

import dev.todaystock.api.member.entity.Member
import java.util.*

class MemberResponse(
    val uuid: UUID?,
    val email: String,
    val nickname: String?,
    val name: String,
    val role: String,
    val deeplink: String?
) {
    companion object {
        fun fromMember(member: Member): MemberResponse = MemberResponse(
            uuid = member.uuid,
            email = member.email,
            nickname = member.nickname,
            name = member.name,
            role = member.role.name,
            deeplink = member.deeplink
        )
    }
}
