package dev.todaystock.api.member.entity

import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val uuid: String,
    private val email: String,
    private val password: String,
    private val nickname: String?,
    private val name: String,
    private val role: String,
    private var deeplink: String?
): UserDetails, User() {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${role}"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

}