package dev.todaystock.api.dto.info

import dev.todaystock.api.member.entity.MemberRoleType
import dev.todaystock.api.member.entity.Member
import java.util.*

class MemberResponse(
    private val uuid: UUID?,
    private val email: String,
    private val nickname: String?,
    private val name: String,
    private val role: MemberRoleType,
    private val deeplink: String?
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
