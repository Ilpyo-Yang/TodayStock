package dev.todaystock.api.member.dto

import dev.todaystock.api.member.entity.Member
import java.util.*

class MemberResponse(
    private val uuid: UUID?,
    private val email: String,
    private val nickname: String?,
    private val name: String,
    private val role: String,
    private val deeplink: String?
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
