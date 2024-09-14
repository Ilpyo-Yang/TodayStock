package dev.todaystock.api.member.service

import dev.todaystock.api.member.entity.CustomUserDetails
import dev.todaystock.api.member.entity.Member
import dev.todaystock.api.member.repository.MemberRepository
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
        memberRepository.findByEmail(username)
            ?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("There is no matching user")

    private fun createUserDetails(member: Member): CustomUserDetails =
        CustomUserDetails(
            member.uuid.toString(),
            member.email,
            passwordEncoder.encode(member.password),
            member.nickname?: "",
            member.name,
            member.role.name,
            member.deeplink?: ""
        )
}