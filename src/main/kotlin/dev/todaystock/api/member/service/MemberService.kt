package dev.todaystock.api.member.service

import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.common.exception.InvalidRequestException
import dev.todaystock.api.common.exception.NoSuchElementException
import dev.todaystock.api.common.security.TokenProvider
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.MemberResponse
import dev.todaystock.api.member.dto.SigninRequest
import dev.todaystock.api.member.entity.CustomUserDetails
import dev.todaystock.api.member.entity.Member
import dev.todaystock.api.member.repository.MemberRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder
) {
    fun signup(request: MemberRequest): MemberResponse? {
        request.password = passwordEncoder.encode(request.password)
        memberRepository.findByEmail(request.email)
            ?.let { throw InvalidRequestException("Email is already in use.") }
            ?: return MemberResponse.fromMember(memberRepository.save(MemberRequest.toMember(request)))
    }

    fun signin(signinRequest: SigninRequest): TokenDto {
        memberRepository.findByEmail(signinRequest.email)
            ?.let {
                if(passwordEncoder.matches(signinRequest.password, it.password)) {
                    val userDetails: CustomUserDetails = userDetailsService.loadUserByUsername(signinRequest.email)
                    val authentication: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    return tokenProvider.createToken(authentication)
                }
                throw InvalidRequestException("Password invalid")
            }
            ?: throw NoSuchElementException("No user associated with this email")
    }

    fun delete(userDetails: CustomUserDetails, confirmPassword: String) {
        if(!passwordEncoder.matches(userDetails.password, confirmPassword)) {
            throw InvalidRequestException("Password invalid")
        }
        memberRepository.findByEmail(userDetails.username)
            ?.let {
                it.deletedDate = LocalDateTime.now()
                memberRepository.save(it)
            }
            ?: throw NoSuchElementException("No user associated with this email")
    }
}