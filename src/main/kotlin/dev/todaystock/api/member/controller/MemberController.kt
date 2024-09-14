package dev.todaystock.api.member.controller

import dev.todaystock.api.common.dto.ApiResponse
import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.MemberResponse
import dev.todaystock.api.member.dto.SigninRequest
import dev.todaystock.api.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signup(@RequestBody(required = true) @Valid memberRequest: MemberRequest): ResponseEntity<ApiResponse<MemberResponse>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(memberService.signup(memberRequest)))
    }

    @PostMapping("/signin")
    fun signup(@RequestBody(required = true) @Valid signinRequest: SigninRequest): ResponseEntity<ApiResponse<TokenDto>> {
        return ResponseEntity.ok(ApiResponse.successDataResponse(memberService.signin(signinRequest)))
    }

    // admin
    @DeleteMapping
    fun signup(@RequestBody(required = true) email: String): ResponseEntity<ApiResponse<Nothing>> {
        memberService.delete(email)
        return ResponseEntity.ok(ApiResponse.successResponse())
    }

//    @GetMapping
//    fun signup(@AuthenticationPrincipal LoginUser loginUser): ResponseEntity<ApiResponse<MemberResponse>> {
//        return ResponseEntity.ok().body(ApiResponse.successDataResponse(memberService.findByEmail(loginUser.username)))
//    }
}