package dev.todaystock.api.common.security

import dev.todaystock.api.member.entity.Member
import dev.todaystock.api.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByEmail(username).orElseThrow()
            ?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("There is no matching user")

    private fun createUserDetails(member: Member): UserDetails =
        User(
            member.email,
            passwordEncoder.encode(member.password),
            member.role.name.map { SimpleGrantedAuthority("ROLE_${member.role.name}") }
        )
}