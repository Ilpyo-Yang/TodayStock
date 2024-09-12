package dev.todaystock.api.member.service

import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.common.exception.InvalidRequestException
import dev.todaystock.api.common.security.TokenProvider
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.MemberResponse
import dev.todaystock.api.member.dto.SigninRequest
import dev.todaystock.api.member.entity.Member
import dev.todaystock.api.member.repository.MemberRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val tokenProvider: TokenProvider
) {
    fun signup(request: MemberRequest): MemberResponse? {
        if (memberRepository.findByEmail(request.email).isPresent) {
            throw InvalidRequestException("Email")
        }
        return MemberResponse.fromMember(memberRepository.save(MemberRequest.toMember(request)))
    }

    fun signin(signinRequest: SigninRequest): TokenDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(signinRequest.email, signinRequest.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return tokenProvider.createToken(authentication)
    }

    fun delete(email: String) {
        val member: Member = memberRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Inappropriate Request") }
        return memberRepository.delete(member)
    }

    fun findByEmail(email: String): Member? {
        return memberRepository.findByEmail(email).orElseThrow { NoSuchElementException("Email is NOT registered") }
    }
}