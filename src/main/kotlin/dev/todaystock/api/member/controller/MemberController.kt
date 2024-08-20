package dev.todaystock.api.member.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.MemberResponse
import dev.todaystock.api.member.dto.SigninRequest
import dev.todaystock.api.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping
    fun signup(@RequestBody(required = true) @Valid memberRequest: MemberRequest): ResponseEntity<ApiResponse<MemberResponse>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(memberService.signup(memberRequest)))
    }

    @PostMapping("signin")
    fun signup(@RequestBody(required = true) @Valid signinRequest: SigninRequest): ResponseEntity<ApiResponse<TokenDto>> {
        return ResponseEntity.ok().body(ApiResponse.successDataResponse(memberService.signin(signinRequest)))
    }
}