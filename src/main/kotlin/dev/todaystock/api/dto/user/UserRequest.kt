package dev.todaystock.api.dto.info

import dev.todaystock.api.entity.*
import dev.todaystock.api.entity.type.UserRoleType
import java.util.*

class UserRequest(
    var uuid: UUID?,
    var email: String,
    var password: String,
    var nickname: String?,
    var name: String,
    var role: UserRoleType,
    var deeplink: String?
) {
    companion object {
        fun toCompanyInfo(userRequest: UserRequest): User = User(
            uuid = userRequest.uuid,
            email = userRequest.email,
            password = userRequest.password,
            nickname = userRequest.nickname?.let { randomUsername() },
            name = userRequest.name,
            role = userRequest.role,
            deeplink = userRequest.deeplink
        )

        fun randomUsername(): String {
            // todo
            return ""
        }
    }


}
