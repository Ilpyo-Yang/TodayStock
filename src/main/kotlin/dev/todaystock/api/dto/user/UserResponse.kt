package dev.todaystock.api.dto.info

import dev.todaystock.api.entity.*
import dev.todaystock.api.entity.type.UserRoleType
import java.util.*

class UserResponse(
    var uuid: UUID?,
    var email: String,
    var nickname: String?,
    var name: String,
    var role: UserRoleType,
    var deeplink: String?
) {
    companion object {
        fun fromCompanyInfo(user: User): UserResponse = UserResponse(
            uuid = user.uuid,
            email = user.email,
            nickname = user.nickname,
            name = user.name,
            role = user.role,
            deeplink = user.deeplink
        )
    }
}
