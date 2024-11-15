package dev.todaystock.api.member.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.member.dto.ConfirmPasswordRequest
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.MemberResponse
import dev.todaystock.api.member.dto.SigninRequest
import dev.todaystock.api.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
@Tag(name = "Member API", description = "사용자 설정과 관련된 API입니다.")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 API")
    fun signup(@RequestBody(required = true) @Valid memberRequest: MemberRequest): ResponseEntity<ApiResponse<MemberResponse>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(memberService.signup(memberRequest)))
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "로그인 API")
    fun signin(@RequestBody(required = true) @Valid signinRequest: SigninRequest): ResponseEntity<ApiResponse<TokenDto>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(memberService.signin(signinRequest)))
    }

    @PostMapping("/delete")
    @Operation(summary = "회원삭제", description = "회원삭제 API")
    fun delete(@AuthenticationPrincipal loginUser: User,
               @RequestBody(required = true) @Valid request: ConfirmPasswordRequest
    ): ResponseEntity<out ApiResponse<out HttpStatus>> {
        memberService.delete(loginUser, request.password)
        return ResponseEntity.ok(ApiResponse.successResponse())
    }
}