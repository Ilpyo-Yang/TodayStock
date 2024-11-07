package dev.todaystock.api.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import dev.todaystock.api.common.annotation.ValidEnum
import dev.todaystock.api.member.entity.Member
import dev.todaystock.api.member.entity.MemberRoleType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.util.*

class MemberRequest(
    @JsonProperty("uuid")
    private val _uuid: String?,

    @JsonProperty("email")
    @field:NotBlank(message = "Email name cannot be empty")
    @field:Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = "이메일 형식이 아닙니다.")
    private val _email: String,

    @JsonProperty("password")
    @field:NotBlank(message = "Password name cannot be empty")
    @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#\\\$%\\^&\\*])(?=\\S+\$).{8,}\$",
        message = "비밀번호는 최소 8자리 이상이며, 숫자, 문자(대문자, 소문자 구분하지 않음), 특수문자 각각 최소 하나 이상을 포함하고, 공백이 없어야 합니다.")
    private var _password: String,

    @JsonProperty("nickname")
    private val _nickname: String?,

    @JsonProperty("name")
    @field:NotBlank(message = "Name name cannot be empty")
    private val _name: String,

    @JsonProperty("role")
    @field:ValidEnum(enumClass = MemberRoleType::class, message = "Choose one of MemberRoleType")
    private val _role: String?,

    @JsonProperty("deeplink")
    private val _deeplink: String?
) {
    val uuid: UUID?
        get() = _uuid?.let { UUID.fromString(it) }
    val email: String
        get() = _email
    var password: String
        get() = _password
        set(newPassword: String) { _password = newPassword }
    val nickname: String
        get() = _nickname?: randomUserNickname()
    val name: String
        get() = _name
    val role: MemberRoleType
        get() = MemberRoleType.valueOf(_role?: MemberRoleType.Member.name)
    val deeplink: String?
        get() = _deeplink

    companion object {
        fun toMember(memberRequest: MemberRequest): Member = Member(
            uuid = memberRequest.uuid,
            email = memberRequest.email,
            password = memberRequest.password,
            nickname = memberRequest.nickname,
            name = memberRequest.name,
            role = memberRequest.role,
            deeplink = memberRequest.deeplink
        )

        fun randomUserNickname(): String {
            val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            return (1..10)
                .map { chars.random() }
                .joinToString("")
        }
    }


}
